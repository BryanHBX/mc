package org.edu.timelycourse.mc.biz.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by x36zhao on 2018/4/19.
 */
@Data
public class OptionProperty<K, V> implements Serializable
{
    private K key;
    private V value;

    public OptionProperty () {}
    public OptionProperty (K key, V value)
    {
        this.key = key;
        this.value = value;
    }

    public static OptionProperty<Integer, String> from (Integer key, Object object, String fieldName)
    {
        if (key != null)
        {
            Object value = ReflectUtil.getValue(object, fieldName);
            OptionProperty<Integer, String> option = new OptionProperty<>();
            option.setKey(key);
            option.setValue(value != null ? value.toString() : null);
            return option;
        }
        return null;
    }
}
