package org.edu.timelycourse.mc.biz.entity.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.edu.timelycourse.mc.biz.entity.BaseEntity;

/**
 * Created by x36zhao on 2017/3/16.
 */
@Data
@ToString(exclude = "id")
public class SystemConfigValue extends BaseEntity
{
    @ApiModelProperty(hidden = true)
    private int configId;

    private String configValue;
}
