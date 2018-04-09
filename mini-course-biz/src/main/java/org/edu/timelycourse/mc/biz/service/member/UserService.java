package org.edu.timelycourse.mc.biz.service.member;

import com.google.common.base.Enums;
import com.google.common.base.Strings;
import org.edu.timelycourse.mc.biz.enums.EUserRole;
import org.edu.timelycourse.mc.biz.enums.EUserStatus;
import org.edu.timelycourse.mc.biz.enums.EUserType;
import org.edu.timelycourse.mc.biz.model.member.User;
import org.edu.timelycourse.mc.biz.repository.member.UserRepository;
import org.edu.timelycourse.mc.biz.repository.school.SchoolInfoRepository;
import org.edu.timelycourse.mc.biz.service.BaseService;
import org.edu.timelycourse.mc.biz.utils.Asserts;
import org.edu.timelycourse.mc.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

/**
 * Created by x36zhao on 2017/3/17.
 */
@Service
public class UserService extends BaseService<User>
{
    private static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;
    private SchoolInfoRepository schoolInfoRepository;

    @Autowired
    public UserService(UserRepository userRepository, SchoolInfoRepository schoolInfoRepository)
    {
        super(userRepository);
        this.userRepository = userRepository;
        this.schoolInfoRepository = schoolInfoRepository;
    }

    @Override
    public User add (User entity)
    {
        Asserts.assertEntityNotNullById(schoolInfoRepository, entity.getSchoolId());

        if (!isUserEntityValid(entity))
        {
            throw new ServiceException(String.format("Invalid user entity %s", entity));
        }

        boolean exists = false;
        if (entity.getUserIdentity() != null)
        {
            exists = (userRepository.getByUserId(entity.getUserIdentity()) != null);
        }

        if (!exists && Strings.isNullOrEmpty(entity.getPhone()))
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
                "User with ( phone - %s, identity - %s ) already exists",
                entity.getPhone(),
                entity.getUserIdentity()
        ));
    }

    @Override
    public User update(User entity)
    {
        Asserts.assertEntityNotNullById(schoolInfoRepository, entity.getSchoolId());

        if (!isUserEntityValid(entity))
        {
            throw new ServiceException(String.format("Invalid user entity %s", entity));
        }

        boolean valid = true;
        if (entity.getUserIdentity() != null)
        {
            User user = userRepository.getByUserId(entity.getUserIdentity());
            if (user != null && !user.getId().equals(entity.getId()))
            {
                valid = false;
            }
        }

        if (valid && Strings.isNullOrEmpty(entity.getPhone()))
        {
            User user = userRepository.getByUserPhone(entity.getPhone());
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
                "User with ( phone - %s, identity - %s ) already exists",
                entity.getPhone(),
                entity.getUserIdentity()
        ));
    }

    private boolean isUserEntityValid (final User entity)
    {
        return EUserStatus.hasValue(entity.getStatus()) &&
                EUserRole.hasValue(entity.getStatus()) &&
                EUserType.hasValue(entity.getType());
    }

    public User findByUserIdentity (String userIdentity)
    {
        if (Strings.isNullOrEmpty(userIdentity))
        {
            return userRepository.getByUserId(userIdentity);
        }

        throw new ServiceException(String.format("Invalid user identity %s",  userIdentity));
    }

    public List<User> findBySchoolId (Integer schoolId)
    {
        if (schoolId != null && schoolId > 0)
        {
            return userRepository.getBySchoolId(schoolId);
        }

        throw new ServiceException(String.format("Invalid school id %d",  schoolId));
    }

    public User findByUserPhone (String userPhone)
    {
        if (Strings.isNullOrEmpty(userPhone))
        {
            return userRepository.getByUserPhone(userPhone);
        }

        throw new ServiceException(String.format("Invalid user phone %s",  userPhone));
    }

    public static void main (String[] args)
    {
        String value = "1";
        System.out.println(Enums.getIfPresent(EUserStatus.class, "ENABLED").isPresent());
    }
}
