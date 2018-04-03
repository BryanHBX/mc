package org.edu.energycourse.mc.biz.repository.member;

import org.apache.ibatis.annotations.Param;
import org.edu.energycourse.mc.biz.entity.member.UserRole;
import org.edu.energycourse.mc.biz.repository.BaseRepository;
import org.springframework.stereotype.Component;

/**
 * Created by x36zhao on 2017/3/17.
 */
@Component
public interface UserRoleRepository extends BaseRepository<UserRole>
{
    UserRole getByUserId (@Param("uid") Integer id);
    Integer deleteByUserId (@Param("uid") Integer userId);
}
