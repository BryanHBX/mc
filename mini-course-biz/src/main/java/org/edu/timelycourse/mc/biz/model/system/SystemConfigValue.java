package org.edu.timelycourse.mc.biz.model.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.edu.timelycourse.mc.biz.model.BaseEntity;

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
