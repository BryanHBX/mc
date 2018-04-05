package org.edu.timelycourse.mc.biz.repository.system;

import org.apache.ibatis.annotations.Param;
import org.edu.timelycourse.mc.biz.entity.system.SystemConfig;
import org.edu.timelycourse.mc.biz.repository.BaseRepository;
import org.springframework.stereotype.Component;

/**
 * Created by x36zhao on 2018/3/31.
 */
@Component
public interface SystemConfigRepository extends BaseRepository<SystemConfig>
{
    SystemConfig getByPropertyName(@Param("name") String propertyName);
    Integer deleteByPropertyName(@Param("name") String propertyName);
}
