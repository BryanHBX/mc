package org.edu.timelycourse.mc.biz.repository;

import org.apache.ibatis.annotations.Param;
import org.edu.timelycourse.mc.beans.model.SystemConfigModel;
import org.edu.timelycourse.mc.biz.repository.BaseRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by x36zhao on 2018/3/31.
 */
@Component
public interface SystemConfigRepository extends BaseRepository<SystemConfigModel>
{
    SystemConfigModel getByConfigName(@Param("name") String configName);
    List<SystemConfigModel> getChildrenConfig (@Param("parentId") Integer parentId);
    Integer deleteByConfigName(@Param("name") String configName);
}
