package org.edu.timelycourse.mc.biz.service;

import org.edu.timelycourse.mc.biz.enums.EUserRole;
import org.edu.timelycourse.mc.biz.enums.EUserStatus;
import org.edu.timelycourse.mc.biz.enums.EUserType;
import org.edu.timelycourse.mc.biz.model.*;
import org.edu.timelycourse.mc.biz.paging.PagingBean;
import org.edu.timelycourse.mc.biz.repository.*;
import org.edu.timelycourse.mc.biz.utils.Asserts;
import org.edu.timelycourse.mc.common.exception.ServiceException;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.edu.timelycourse.mc.common.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.swing.text.html.parser.Entity;
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
        // TODO replace using real code
        entity.setStatus(EUserStatus.ENABLED.code());
        entity.setType(EUserType.INSTITUTION.code());
        entity.setRole(EUserRole.EMPLOYEE.code());

        if (!entity.isValidInput())
        {
            throw new ServiceException(String.format("Invalid user entity %s", entity));
        }

        Asserts.assertEntityNotNullById(schoolInfoRepository, entity.getSchoolId());

        boolean exists  = (userRepository.getByEntity(new UserModel(
                entity.getUserIdentity(), entity.getPhone(), entity.getWxId())) != null);

        if (!exists)
        {
            checkUserData(entity);

            entity.setSubjectsId(StringUtil.join(entity.getSubjectIds()));
            entity.setCoursesId(StringUtil.join(entity.getCourseIds()));
            entity.setGradesId(StringUtil.join(entity.getGradeIds()));

            initUserPassword(entity);
            //entity.setPassword(new BCryptPasswordEncoder().encode(entity.getPhone()));
            entity.setCreationTime(new Date());

            repository.insert(entity);

            // add user roles
            addUserRole(entity);

            return entity;
        }

        throw new ServiceException(String.format("User already exists with - [phone:%s, identity: %s, wxId: %s]",
                entity.getPhone(), entity.getUserIdentity(), entity.getWxId()
        ));
    }

    @Override
    public UserModel update(UserModel entity)
    {
        Asserts.assertEntityNotNullById(schoolInfoRepository, entity.getSchoolId());

        if (!isUserEntityValid(entity))
        {
            throw new ServiceException(String.format("Invalid user entity %s", entity));
        }

        boolean valid = true;
        if (StringUtil.isNotEmpty(entity.getUserIdentity()))
        {
            UserModel user = userRepository.getByUserId(entity.getUserIdentity());
            if (user != null && !user.getId().equals(entity.getId()))
            {
                valid = false;
            }
        }

        if (valid && StringUtil.isNotEmpty(entity.getPhone()))
        {
            UserModel user = userRepository.getByUserPhone(entity.getPhone());
            if (user != null && !user.getId().equals(entity.getId()))
            {
                valid = false;
            }
        }

        if (valid)
        {
            entity.setLastUpdateTime(new Date());
            //initUserPassword(entity);
            //entity.setPassword(new BCryptPasswordEncoder().encode(entity.getPassword()));
            return super.update(entity);
        }

        throw new ServiceException(String.format(
                "UserModel with ( phone - %s, identity - %s ) already exists",
                entity.getPhone(),
                entity.getUserIdentity()
        ));
    }

    @Override
    public UserModel get(Integer id)
    {
        UserModel user = super.get(id);
        enrichUserInfo(user);
        return user;
    }

    @Override
    public PagingBean<UserModel> findByPage(UserModel entity, Integer pageNum, Integer pageSize)
    {
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
        user.setRolesDesc(getRoleTexts(user.getAuthorities()));
    }

    private String getRoleTexts (final List<UserRoleModel> roles)
    {
        if (roles != null && roles.size() > 0)
        {
            StringBuilder builder = new StringBuilder();
            for (UserRoleModel role : roles)
            {
                SystemRoleModel roleModel = roleRepository.getByAlias(role.getRole());
                if (roleModel != null)
                {
                    builder.append(roleModel.getRoleName());
                    builder.append(",");
                }
            }

            if (builder.length() > 0)
            {
                builder.deleteCharAt(builder.length() - 1);
            }

            return builder.toString();
        }
        return null;
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

    private boolean isUserEntityValid (final UserModel entity)
    {
        return EUserStatus.hasValue(entity.getStatus()) &&
                EUserRole.hasValue(entity.getStatus()) &&
                EUserType.hasValue(entity.getType());
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

        user.setPassword(new BCryptPasswordEncoder().encode(initPassword));
    }

}
