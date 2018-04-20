package org.edu.timelycourse.mc.biz.repository;

import org.apache.ibatis.annotations.Param;
import org.edu.timelycourse.mc.beans.model.SystemRoleModel;
import org.springframework.stereotype.Component;

/**
 * Created by x36zhao on 2018/3/31.
 */
@Component
public interface SystemRoleRepository extends BaseRepository<SystemRoleModel>
{
    SystemRoleModel getByAlias(@Param("alias") String aliasName);
}
