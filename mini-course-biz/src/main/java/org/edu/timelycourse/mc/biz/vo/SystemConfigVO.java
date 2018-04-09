package org.edu.timelycourse.mc.biz.vo;

import lombok.Data;
import org.dozer.DozerBeanMapper;
import org.edu.timelycourse.mc.biz.model.system.SystemConfig;

import java.io.Serializable;

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
