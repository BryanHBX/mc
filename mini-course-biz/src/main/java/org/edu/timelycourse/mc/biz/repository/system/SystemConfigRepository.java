package org.edu.timelycourse.mc.biz.repository.system;

import org.apache.ibatis.annotations.Param;
import org.edu.timelycourse.mc.biz.model.system.SystemConfig;
import org.edu.timelycourse.mc.biz.repository.BaseRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by x36zhao on 2018/3/31.
 */
@Component
public interface SystemConfigRepository extends BaseRepository<SystemConfig>
{
    SystemConfig getByConfigName(@Param("name") String configName);
    List<SystemConfig> getChildrenConfig (@Param("parentId") Integer parentId);
    Integer deleteByConfigName(@Param("name") String configName);
}
