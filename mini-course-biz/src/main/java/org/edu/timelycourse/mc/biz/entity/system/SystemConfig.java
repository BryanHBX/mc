package org.edu.timelycourse.mc.biz.entity.system;

import lombok.Data;
import lombok.ToString;
import org.edu.timelycourse.mc.biz.entity.BaseEntity;

/**
 * Created by x36zhao on 2017/3/16.
 */
@Data
@ToString(exclude = "id")
public class SystemConfig extends BaseEntity
{
    private String propertyName;
    private String propertyValue;
    private String propertyDescription;
}
