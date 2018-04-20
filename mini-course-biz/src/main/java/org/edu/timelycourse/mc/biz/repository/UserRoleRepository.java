package org.edu.timelycourse.mc.biz.repository;

import org.apache.ibatis.annotations.Param;
import org.edu.timelycourse.mc.beans.model.UserRoleModel;
import org.edu.timelycourse.mc.biz.repository.BaseRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by x36zhao on 2017/3/17.
 */
@Component
public interface UserRoleRepository extends BaseRepository<UserRoleModel>
{
    List<UserRoleModel> getByUserId (@Param("uid") Integer id);
    Integer deleteByUserId (@Param("uid") Integer userId);
}
