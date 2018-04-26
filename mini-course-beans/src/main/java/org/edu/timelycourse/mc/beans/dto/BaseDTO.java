package org.edu.timelycourse.mc.beans.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by x36zhao on 2018/4/19.
 */
@Data
public abstract class BaseDTO implements Serializable
{
    /**
     * 实体ID
     */
    protected Integer id;

    /**
     * 学校ID
     */
    @JsonIgnore
    private Integer schoolId;

    @JsonIgnore
    public abstract boolean isValid();
}
