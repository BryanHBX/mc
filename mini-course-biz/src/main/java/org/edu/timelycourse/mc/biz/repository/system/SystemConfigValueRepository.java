package org.edu.timelycourse.mc.biz.repository.system;

import org.apache.ibatis.annotations.Param;
import org.edu.timelycourse.mc.biz.entity.system.SystemConfig;
import org.edu.timelycourse.mc.biz.entity.system.SystemConfigValue;
import org.edu.timelycourse.mc.biz.repository.BaseRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by x36zhao on 2018/3/31.
 */
@Component
public interface SystemConfigValueRepository extends BaseRepository<SystemConfigValue>
{
    List<SystemConfigValue> getByConfigId(@Param("configId") Integer configId);
    Integer deleteByConfigId(@Param("configId") Integer configId);
    SystemConfigValue getByConfigValue (@Param("value") String configValue);
}
