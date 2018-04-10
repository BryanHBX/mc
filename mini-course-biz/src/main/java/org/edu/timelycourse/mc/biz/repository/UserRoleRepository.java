package org.edu.timelycourse.mc.biz.repository;

import org.apache.ibatis.annotations.Param;
import org.edu.timelycourse.mc.biz.model.UserRoleModel;
import org.edu.timelycourse.mc.biz.repository.BaseRepository;
import org.springframework.stereotype.Component;

/**
 * Created by x36zhao on 2017/3/17.
 */
@Component
public interface UserRoleRepository extends BaseRepository<UserRoleModel>
{
    UserRoleModel getByUserId (@Param("uid") Integer id);
    Integer deleteByUserId (@Param("uid") Integer userId);
}
