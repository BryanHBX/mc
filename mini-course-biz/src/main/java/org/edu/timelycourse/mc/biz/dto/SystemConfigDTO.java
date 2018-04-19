package org.edu.timelycourse.mc.biz.dto;

import lombok.Data;
import org.dozer.DozerBeanMapper;
import org.edu.timelycourse.mc.biz.model.SystemConfigModel;

import java.io.Serializable;

@Data
public class SystemConfigDTO implements Serializable
{
    private Integer id;
    private String configName;
    private String configDescription;

    public static SystemConfigDTO from (final SystemConfigModel entity)
    {
        return new DozerBeanMapper().map(entity, SystemConfigDTO.class);
    }
}
