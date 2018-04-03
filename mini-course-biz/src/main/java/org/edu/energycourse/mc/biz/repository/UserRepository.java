package org.edu.energycourse.mc.biz.repository;

import org.apache.ibatis.annotations.Mapper;
import org.edu.energycourse.mc.biz.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by x36zhao on 2018/3/31.
 */
@Component
public interface UserRepository
{
    Integer insertUser(final User user);
    Integer updateUser(final User user);
    User getByUserId(@Param("id") Integer userId);
    Integer deleteByUserId(@Param("id") Integer userId);
    List<User> getAllUsers();
}
