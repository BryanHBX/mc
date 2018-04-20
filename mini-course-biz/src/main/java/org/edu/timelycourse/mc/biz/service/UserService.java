package org.edu.timelycourse.mc.biz.service;

import org.edu.timelycourse.mc.beans.enums.EUserRole;
import org.edu.timelycourse.mc.beans.enums.EUserStatus;
import org.edu.timelycourse.mc.beans.enums.EUserType;
import org.edu.timelycourse.mc.beans.model.*;
import org.edu.timelycourse.mc.beans.paging.PagingBean;
import org.edu.timelycourse.mc.biz.repository.*;
import org.edu.timelycourse.mc.biz.utils.Asserts;
import org.edu.timelycourse.mc.biz.utils.PasswordUtil;
import org.edu.timelycourse.mc.biz.utils.SecurityContextHelper;
import org.edu.timelycourse.mc.common.exception.ServiceException;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.edu.timelycourse.mc.common.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by x36zhao on 2017/3/17.
 */
@Service
public class UserService extends BaseService<UserModel>
{
    private static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private static final Integer DEFAULT_PWD_LENGTH = 6;

    private UserRepository userRepository;
    private SchoolRepository schoolInfoRepository;
    private SystemRoleRepository roleRepository;
    private SystemConfigRepository configRepository;
    private SchoolProductRepository productRepository;
    private UserRoleRepository userRoleRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       SchoolRepository schoolInfoRepository,
                       SystemRoleRepository roleRepository,
                       SystemConfigRepository configRepository,
                       SchoolProductRepository productRepository,
                       UserRoleRepository userRoleRepository)
    {
        super(userRepository);
        this.userRepository = userRepository;
        this.schoolInfoRepository = schoolInfoRepository;
        this.roleRepository = roleRepository;
        this.configRepository = configRepository;
        this.productRepository = productRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserModel add (UserModel entity)
    {
        entity.setStatus(EUserStatus.ENABLED.code());
        entity.setSchoolId(SecurityContextHelper.getSchoolIdFromPrincipal());

        // TODO
        entity.setType(EUserType.INSTITUTION.code());
        entity.setRole(EUserRole.EMPLOYEE.code());

        // check input
        if (!entity.isValidInput())
        {
            throw new ServiceException(String.format("Invalid user entity %s", entity));
        }

        // check roles
        if (entity.getRoleIds() == null || entity.getRoleIds().size() == 0)
        {
            throw new ServiceException(String.format("Role must be assigned to user: %s", entity));
        }

        // check school
        Asserts.assertEntityNotNullById(schoolInfoRepository, entity.getSchoolId());

        // check id card, mobile and wx id
        boolean exists  = (userRepository.getByEntity(new UserModel(
                entity.getUserIdentity(), entity.getPhone(), entity.getWxId())) != null);

        if (!exists)
        {
            checkUserData(entity);

            entity.setSubjectsId(StringUtil.join(entity.getSubjectIds()));
            entity.setCoursesId(StringUtil.join(entity.getCourseIds()));
            entity.setGradesId(StringUtil.join(entity.getGradeIds()));

            initUserPassword(entity);
            entity.setCreationTime(new Date());

            repository.insert(entity);

            // add user roles
            addUserRole(entity);

            return entity;
        }

        throw new ServiceException(String.format(
                "User already exists with - [phone:%s, identity: %s, wxId: %s]",
                entity.getPhone(), entity.getUserIdentity(), entity.getWxId()
        ));
    }

    @Override
    public UserModel update(UserModel entity)
    {
        if (!entity.isValidInput() && !EntityUtils.isValidEntityId(entity.getId()) &&
                !StringUtil.isNotEmpty(entity.getUserIdentity(), entity.getPhone(), entity.getWxId()))
        {
            throw new ServiceException(String.format("Invalid user entity %s", entity));
        }

        // check user existence in db
        UserModel entityInDb = (UserModel) Asserts.assertEntityNotNullById(repository, entity.getId());

        // check school id if any change in the request
        if (entityInDb.getSchoolId() != entity.getSchoolId())
        {
            throw new ServiceException(String.format(
                    "It's not allowed to change school from %d to %d",
                    entityInDb.getSchoolId(), entity.getSchoolId()
            ));
        }

        entity.setSchoolId(entityInDb.getSchoolId());
        entity.setAuthorities(entityInDb.getAuthorities());

        // check id card, mobile and wx id
        UserModel userByCriteria  = userRepository.getByEntity(new UserModel(
                entity.getUserIdentity(), entity.getPhone(), entity.getWxId()));

        if (userByCriteria != null && !userByCriteria.getId().equals(entity.getId()))
        {
            throw new ServiceException(String.format(
                    "User already exists with - [phone:%s, identity: %s, wxId: %s]",
                    entity.getPhone(), entity.getUserIdentity(), entity.getWxId()
            ));
        }

        // update role
        updateUserRole(entity);

        entity.setSubjectsId(StringUtil.join(entity.getSubjectIds()));
        entity.setCoursesId(StringUtil.join(entity.getCourseIds()));
        entity.setGradesId(StringUtil.join(entity.getGradeIds()));

        entity.setLastUpdateTime(new Date());
        repository.update(entity);
        return entity;
    }

    private void updateUserRole (UserModel entity)
    {
        // check user roles
        if (entity.getRoleIds() == null || entity.getRoleIds().size() == 0)
        {
            throw new ServiceException(String.format("Role must be assigned to user: %s", entity));
        }

        for (Integer roleId : entity.getRoleIds())
        {
            SystemRoleModel systemRole = (SystemRoleModel) Asserts.assertEntityNotNullById(roleRepository, roleId);
            boolean exists = false;
            for (UserRoleModel userRole : entity.getAuthorities())
            {
                if (userRole.getRole().equals(systemRole.getRoleAlias()))
                {
                    exists = true;
                    break;
                }
            }
            if (!exists)
            {
                userRoleRepository.insert(new UserRoleModel(entity.getId(), systemRole.getRoleAlias()));
            }
        }

        for (UserRoleModel userRole : entity.getAuthorities())
        {
            boolean exists = false;
            for (Integer roleId : entity.getRoleIds())
            {
                SystemRoleModel systemRole = (SystemRoleModel) Asserts.assertEntityNotNullById(roleRepository, roleId);
                if (systemRole.getRoleAlias().equals(userRole.getRole()))
                {
                    exists = true;
                    break;
                }
            }

            if (!exists)
            {
                userRoleRepository.delete(userRole.getId());
            }
        }

        // update roles

        entity.getAuthorities().stream().filter(s -> entity.getRoleIds().contains(s.getId()));

        for (UserRoleModel roleModel : entity.getAuthorities())
        {

        }
    }

    @Override
    public UserModel get(Integer id)
    {
        UserModel user = super.get(id);
        enrichUserInfo(user);
        return user;
    }

    public Integer resetPassword (Integer userId, String password)
    {
        if (EntityUtils.isValidEntityId(userId))
        {
            if (StringUtil.isNotEmpty(password))
            {
                UserModel user = (UserModel) Asserts.assertEntityNotNullById(repository, userId);
                user.setPassword(PasswordUtil.encode(password));
                return repository.update(user);
            }

            throw new ServiceException("Password cannot be empty");
        }

        throw new ServiceException(String.format("Invalid user id: %d", userId));
    }

    public Integer resetStatus (Integer userId, Integer userStatus)
    {
        if (EntityUtils.isValidEntityId(userId))
        {
            if (EUserStatus.hasValue(userStatus))
            {
                UserModel user = (UserModel) Asserts.assertEntityNotNullById(repository, userId);
                user.setStatus(userStatus);
                return repository.update(user);
            }

            throw new ServiceException(String.format("Invalid user status: %d", userStatus));
        }

        throw new ServiceException(String.format("Invalid user id: %d", userId));
    }

    @Override
    public PagingBean<UserModel> findByPage(UserModel entity, Integer pageNum, Integer pageSize)
    {
        entity.setSchoolId(SecurityContextHelper.getSchoolIdFromPrincipal());
        PagingBean<UserModel> users = super.findByPage(entity, pageNum, pageSize);
        if (users.getItems() != null)
        {
            for (UserModel user : users.getItems())
            {
                enrichUserInfo(user);
            }
        }
        return users;
    }

    private void enrichUserInfo (UserModel user)
    {
        user.setGradesDesc(getConfigTextByIds(user.getGradesId()));
        user.setSubjectsDesc(getConfigTextByIds(user.getSubjectsId()));
        user.setCoursesDesc(getProductTextByIds(user.getCoursesId()));
        enrichUserRoleInfo(user);
    }

    private void enrichUserRoleInfo (UserModel user)
    {
        if (user.getAuthorities() != null && user.getAuthorities().size() > 0)
        {
            List<Integer> roleIds = new ArrayList<>();
            StringBuilder builder = new StringBuilder();
            for (UserRoleModel role : user.getAuthorities())
            {
                SystemRoleModel roleModel = roleRepository.getByAlias(role.getRole());
                if (roleModel != null)
                {
                    builder.append(roleModel.getRoleName());
                    builder.append(",");
                    roleIds.add(roleModel.getId());
                }
            }

            if (builder.length() > 0)
            {
                builder.deleteCharAt(builder.length() - 1);
            }

            user.setRolesDesc(builder.toString());
            user.setRoleIds(roleIds);
        }
    }

    private String getProductTextByIds (String ids)
    {
        StringBuilder builder = new StringBuilder();
        if (ids != null)
        {
            String[] idSlices = ids.split(",");
            for (String id : idSlices)
            {
                SchoolProductModel model = productRepository.get(Integer.valueOf(id));
                if (model != null)
                {
                    builder.append(model.getProductName());
                    builder.append(",");
                }
            }

            if (builder.length() > 0)
            {
                builder.deleteCharAt(builder.length() - 1);
            }
        }
        return builder.toString();
    }

    private String getConfigTextByIds (String ids)
    {
        StringBuilder builder = new StringBuilder();
        if (ids != null)
        {
            String[] idSlices = ids.split(",");
            for (String id : idSlices)
            {
                SystemConfigModel model = configRepository.get(Integer.valueOf(id));
                if (model != null)
                {
                    builder.append(model.getConfigDescription());
                    builder.append(",");
                }
            }

            if (builder.length() > 0)
            {
                builder.deleteCharAt(builder.length() - 1);
            }
        }
        return builder.toString();
    }

    public UserModel findByUserIdentity (String userIdentity)
    {
        if (!StringUtil.isNotEmpty(userIdentity))
        {
            return userRepository.getByUserId(userIdentity);
        }

        throw new ServiceException(String.format("Invalid user identity %s",  userIdentity));
    }

    public List<UserModel> findBySchoolId (Integer schoolId)
    {
        if (EntityUtils.isValidEntityId(schoolId))
        {
            return userRepository.getBySchoolId(schoolId);
        }

        throw new ServiceException(String.format("Invalid school id %d",  schoolId));
    }

    public UserModel findByUserPhone (String userPhone)
    {
        if (!StringUtil.isNotEmpty(userPhone))
        {
            return userRepository.getByUserPhone(userPhone);
        }

        throw new ServiceException(String.format("Invalid user phone %s",  userPhone));
    }


    private void addUserRole (final UserModel entity)
    {
        for (Integer roleId : entity.getRoleIds())
        {
            SystemRoleModel systemRole = roleRepository.get(roleId);
            UserRoleModel userRole = new UserRoleModel(entity.getId(), systemRole.getRoleAlias());
            if (userRoleRepository.getByEntity(userRole) == null)
            {
                userRoleRepository.insert(userRole);
            }
        }

    }

    private void checkUserData (final UserModel entity)
    {
        checkProducts(entity.getCourseIds());
        checkSubjectAndGrade(entity.getSubjectIds());
        checkSubjectAndGrade(entity.getGradeIds());

        Assert.notEmpty(entity.getRoleIds(), String.format("Role is not assigned to user: %s", entity));
        checkRoles(entity.getRoleIds());
    }

    private void checkProducts (List<Integer> productIds)
    {
        if (productIds != null && productIds.size() > 0)
        {
            for (Integer productId : productIds)
            {
                Asserts.assertEntityNotNullById(productRepository, productId);
            }
        }
    }

    private void checkSubjectAndGrade (List<Integer> configIds)
    {
        if (configIds != null && configIds.size() > 0)
        {
            for (Integer configId : configIds)
            {
                Asserts.assertEntityNotNullById(configRepository, configId);
            }
        }
    }

    private void checkRoles (List<Integer> roleIds)
    {
        if (roleIds != null && roleIds.size() > 0)
        {
            for (Integer roleId : roleIds)
            {
                Asserts.assertEntityNotNullById(roleRepository, roleId);
            }
        }
    }

    private void initUserPassword (UserModel user)
    {
        String initPassword = user.getPhone();
        if (user.getPhone().length() > DEFAULT_PWD_LENGTH)
        {
            initPassword = user.getPhone().substring(user.getPhone().length() - DEFAULT_PWD_LENGTH);
        }

        user.setPassword(PasswordUtil.encode(initPassword));
    }
}
