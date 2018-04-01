package org.edu.energycourse.mc.common.entity;

import lombok.Data;

/**
 * Created by Marco on 2018/3/31.
 */
@Data
public class ApiResult<T>
{
    private boolean success;
    private T data;
    private String error;

    public ApiResult(T data)
    {
        this(true, data);
    }

    public ApiResult(String error)
    {
        this.error = error;
        this.success = false;
    }

    public ApiResult(boolean success)
    {
        this(success, null);
    }

    public ApiResult(boolean success, T data)
    {
        this(success, data, null);
    }

    public ApiResult(boolean success, T data, String error)
    {
        this.success = success;
        this.error = error;
        this.data = data;
    }
}
