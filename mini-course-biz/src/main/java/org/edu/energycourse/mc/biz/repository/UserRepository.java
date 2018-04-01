package org.edu.energycourse.mc.biz.repository;

import org.edu.energycourse.mc.biz.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by x36zhao on 2018/3/31.
 */
public interface UserRepository
{
    Integer insertUser(final User user);
    Integer updateUser(final User user);
    User getUserByName(@Param("userName") String userName);
    User getUserById(@Param("id") Integer userId);
    Integer deleteUserById(@Param("id") Integer userId);
    Integer deleteUserByName(@Param("userName") String userName);
    List<User> getAllUsers();
}
