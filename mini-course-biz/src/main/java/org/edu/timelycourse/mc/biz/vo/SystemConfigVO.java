package org.edu.timelycourse.mc.biz.vo;

import lombok.Data;
import org.dozer.DozerBeanMapper;
import org.edu.timelycourse.mc.biz.entity.system.SystemConfig;

import java.io.Serializable;
import java.util.List;

@Data
public class SystemConfigVO implements Serializable
{
    private Integer id;
    private String configName;
    private String configDescription;

    public static SystemConfigVO from (final SystemConfig entity)
    {
        return new DozerBeanMapper().map(entity, SystemConfigVO.class);
    }
}
