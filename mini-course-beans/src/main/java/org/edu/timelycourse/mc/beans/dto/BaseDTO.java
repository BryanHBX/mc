package org.edu.timelycourse.mc.beans.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by x36zhao on 2018/4/19.
 */
@Data
public abstract class BaseDTO implements Serializable
{
    private Integer id;
}
