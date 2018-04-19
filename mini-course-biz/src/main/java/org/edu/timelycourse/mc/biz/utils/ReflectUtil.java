package org.edu.timelycourse.mc.biz.utils;

import java.lang.reflect.Field;

/**
 * Created by x36zhao on 2018/4/19.
 */
public final class ReflectUtil
{
    public static Object getValue (Object bean, String fieldName)
    {
        try
        {
            if (bean != null)
            {
                Field[] fields = bean.getClass().getDeclaredFields();
                for (Field field : fields)
                {
                    field.setAccessible(true);
                    if (field.getName().equals(fieldName))
                    {
                        return field.get(bean);
                    }
                }
            }
        }
        catch (IllegalAccessException ex)
        {
        }

        return null;
    }
}
