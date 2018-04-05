package org.edu.timelycourse.mc.common.entity;

import lombok.Data;

/**
 * Created by Marco on 2018/3/31.
 */
@Data
public class ResponseData<T>
{
    private boolean success;
    private T data;
    private String error;

    public ResponseData (T data)
    {
        this(true, data);
    }

    public ResponseData (String error)
    {
        this.error = error;
        this.success = false;
    }

    public ResponseData (boolean success)
    {
        this(success, null);
    }

    public ResponseData (boolean success, T data)
    {
        this(success, data, null);
    }

    public ResponseData (boolean success, T data, String error)
    {
        this.success = success;
        this.data = data;
        if (!success)
        {
            this.error = error;
        }
    }
}
