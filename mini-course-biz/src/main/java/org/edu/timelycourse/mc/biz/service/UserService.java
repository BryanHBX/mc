package org.edu.timelycourse.mc.biz.service;

import org.edu.timelycourse.mc.biz.enums.EUserRole;
import org.edu.timelycourse.mc.biz.enums.EUserStatus;
import org.edu.timelycourse.mc.biz.enums.EUserType;
import org.edu.timelycourse.mc.biz.model.UserModel;
import org.edu.timelycourse.mc.biz.repository.UserRepository;
import org.edu.timelycourse.mc.biz.repository.SchoolRepository;
import org.edu.timelycourse.mc.biz.utils.Asserts;
import org.edu.timelycourse.mc.common.exception.ServiceException;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.edu.timelycourse.mc.common.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by x36zhao on 2017/3/17.
 */
@Service
public class UserService extends BaseService<UserModel>
{
    private static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;
    private SchoolRepository schoolInfoRepository;

    @Autowired
    public UserService(UserRepository userRepository, SchoolRepository schoolInfoRepository)
    {
        super(userRepository);
        this.userRepository = userRepository;
        this.schoolInfoRepository = schoolInfoRepository;
    }

    @Override
    public UserModel add (UserModel entity)
    {
        if (!entity.isValidInput())
        {
            throw new ServiceException(String.format("Invalid user entity %s", entity));
        }

        Asserts.assertEntityNotNullById(schoolInfoRepository, entity.getSchoolId());

        boolean exists = false;
        if (StringUtil.isNotEmpty(entity.getUserIdentity()))
        {
            exists = (userRepository.getByUserId(entity.getUserIdentity()) != null);
        }

        if (!exists && StringUtil.isNotEmpty(entity.getPhone()))
        {
            exists = (userRepository.getByUserPhone(entity.getPhone()) != null);
        }

        if (!exists)
        {
            entity.setPassword(new BCryptPasswordEncoder().encode(entity.getPassword()));
            entity.setCreationTime(new Date());
            return super.add(entity);
        }

        throw new ServiceException(String.format(
                "UserModel with ( phone - %s, identity - %s ) already exists",
                entity.getPhone(),
                entity.getUserIdentity()
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
            entity.setPassword(new BCryptPasswordEncoder().encode(entity.getPassword()));
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

        throw new ServiceException(String.format(
                "Invalid user identity %s",  userIdentity));
    }

    public List<UserModel> findBySchoolId (Integer schoolId)
    {
        if (EntityUtils.isValidEntityId(schoolId))
        {
            return userRepository.getBySchoolId(schoolId);
        }

        throw new ServiceException(String.format(
                "Invalid school id %d",  schoolId));
    }

    public UserModel findByUserPhone (String userPhone)
    {
        if (!StringUtil.isNotEmpty(userPhone))
        {
            return userRepository.getByUserPhone(userPhone);
        }

        throw new ServiceException(String.format(
                "Invalid user phone %s",  userPhone));
    }
}
