package org.edu.energycourse.mc.biz.service;

import org.edu.energycourse.mc.biz.entity.User;
import org.edu.energycourse.mc.biz.entity.UserRole;
import org.edu.energycourse.mc.biz.repository.UserRepository;
import org.edu.energycourse.mc.biz.repository.UserRoleRepository;
import org.edu.energycourse.mc.common.exception.ServiceException;
import org.edu.energycourse.mc.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by x36zhao on 2017/3/17.
 */
@Service
public class UserService extends BaseService
{
    private static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public User findById (Integer userId)
    {
        if (userId != null && userId > 0)
        {
            User user = this.userRepository.getByUserId(userId);
            if (user != null)
            {
                UserRole userRole = this.userRoleRepository.getByUserId(user.getId());
                if (userRole != null)
                {
                    user.setRole(userRole.getRole());
                    user.addRole(userRole);
                }
                return user;
            }

            throw new ServiceException(String.format("User (id: %d) does not exist", userId));
        }

        throw new ServiceException("Invalid user id: " + userId);
    }

    public List<User> getAllUsers ()
    {
        List<User> users = this.userRepository.getAllUsers();
        if (users != null)
        {
            for (User user : users)
            {
                UserRole userRole = this.userRoleRepository.getByUserId(user.getId());
                if (userRole != null)
                {
                    user.setRole(userRole.getRole());
                    user.addRole(userRole);
                }
            }
        }

        return users;
    }

    public User saveOrUpdate (User user, String role)
    {
        return user.getId() != null ? updateUser(user, role) : addUser(user, role);
    }

    private User addUser (User user, String role)
    {
        if (user != null)
        {
            user.setLastLoginTime(new Date());
            Integer result = this.userRepository.insertUser(user);
            if (result > 0)
            {
                UserRole userRole = new UserRole(user.getId(), role);
                userRoleRepository.insertUserRole(userRole);

                return user;
            }

            throw new ServiceException("Failed to add user: " + user);
        }

        throw new ServiceException("Invalid user to be added: " + user);
    }

    private User updateUser (User user, String role)
    {
        if (user != null && user.getId() != null)
        {
            user.setLastLoginTime(new Date());
            Integer result = this.userRepository.updateUser(user);
            if (result > 0)
            {
                UserRole userRole = userRoleRepository.getByUserId(user.getId());
                if (userRole == null)
                {
                    userRole = new UserRole(user.getId(), role);
                }
                else
                {
                    userRole.setRole(role);
                }

                userRoleRepository.updateUserRole(userRole);
                return user;
            }

            throw new ServiceException("Failed to update user: " + user);
        }

        throw new ServiceException("Invalid user to be updated: " + user);
    }

    @Override
    protected String getServiceCategory ()
    {
        return null;
    }

    @Override
    protected String getServiceDomain ()
    {
        return null;
    }
}
