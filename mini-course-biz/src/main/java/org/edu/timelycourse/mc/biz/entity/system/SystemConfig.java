package org.edu.timelycourse.mc.biz.entity.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.edu.timelycourse.mc.biz.entity.BaseEntity;

import java.util.List;

/**
 * Created by x36zhao on 2017/3/16.
 */
@Data
@ToString(exclude = "id")
public class SystemConfig extends BaseEntity
{
    private String configName;
    private String configDescription;
    private int multiple;

    @ApiModelProperty(required = false, hidden = true)
    private List<SystemConfigValue> values;

    public boolean hasMultipleValue ()
    {
        return multiple > 0;
    }
}
