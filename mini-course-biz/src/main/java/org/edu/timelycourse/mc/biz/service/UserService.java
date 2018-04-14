package org.edu.timelycourse.mc.biz.service;

import org.edu.timelycourse.mc.biz.enums.EUserRole;
import org.edu.timelycourse.mc.biz.enums.EUserStatus;
import org.edu.timelycourse.mc.biz.enums.EUserType;
import org.edu.timelycourse.mc.biz.model.SystemRoleModel;
import org.edu.timelycourse.mc.biz.model.UserModel;
import org.edu.timelycourse.mc.biz.model.UserRoleModel;
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
