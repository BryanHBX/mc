package org.edu.energycourse.mc.biz.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.edu.energycourse.mc.biz.entity.UserRole;
import org.springframework.stereotype.Component;

/**
 * Created by x36zhao on 2017/3/17.
 */
@Component
public interface UserRoleRepository
{
    Integer insertUserRole (final UserRole userRole);
    Integer updateUserRole (final UserRole userRole);
    UserRole getByUserId (@Param("id") Integer id);
    UserRole getById (@Param("id") Integer id);
    Integer deleteById (@Param("id") Integer id);
    Integer deleteByUserId (@Param("userId") Integer userId);
}
