package org.edu.timelycourse.mc.biz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Marco on 2018/3/31.
 */
@Getter
public abstract class BaseEntity implements Serializable
{
    private static final long serialVersionUID = -2488885189931569213L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public void setId (Integer id)
    {
        this.id = (!isValidId(id)) ? null : id;
    }

    @JsonIgnore
    public abstract boolean isValid ();

    protected boolean isValidId (Integer... ids)
    {
        if (ids != null && ids.length > 0)
        {
            for (Integer id : ids)
            {
                if (id == null || id <= 0)
                {
                    return false;
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    protected boolean isValidFloat (Double... numbers)
    {
        if (numbers != null && numbers.length > 0)
        {
            for (Double number : numbers)
            {
                if (number == null || number < 0)
                {
                    return false;
                }
            }
            return true;
        }
        else
        {
            return false;
        }
    }
}