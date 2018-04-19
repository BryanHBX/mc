package org.edu.timelycourse.mc.biz.utils;

import lombok.Data;
import org.edu.timelycourse.mc.common.utils.EntityUtils;

import java.io.Serializable;

/**
 * Created by x36zhao on 2018/4/19.
 */
@Data
public class NamedOptionProperty implements Serializable
{
    private Integer id;
    private String name;

    public NamedOptionProperty() {}
    public NamedOptionProperty(Integer id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public static NamedOptionProperty from (Integer id, Object object, String fieldName)
    {
        if (id != null)
        {
            Object value = ReflectUtil.getValue(object, fieldName);
            NamedOptionProperty option = new NamedOptionProperty();
            option.setId(id);
            option.setName(value != null ? value.toString() : null);
            return option;
        }
        return null;
    }

    public Integer getId()
    {
        return id != null && id >= 0 ? id : null;
    }
}
