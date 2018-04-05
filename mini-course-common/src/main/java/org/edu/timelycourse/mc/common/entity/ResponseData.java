package org.edu.timelycourse.mc.common.entity;

import lombok.Data;
import org.edu.timelycourse.mc.common.enums.ResultCode;

import java.io.Serializable;

/**
 * Created by Marco on 2018/3/31.
 */
@Data
public class ResponseData implements Serializable
{
    private boolean success;
    private Object data;
    private String error;

    public ResponseData () {}

    public static ResponseData success ()
    {
        return success(null);
    }

    public static ResponseData success (Object data)
    {
        ResponseData result = new ResponseData();
        result.setData(data);
        result.setSuccess(true);
        return result;
    }

    public static ResponseData failure (ResultCode resultCode)
    {
        return failure(resultCode, null);
    }

    public static ResponseData failure (ResultCode resultCode, Object data)
    {
        ResponseData result = new ResponseData();
        result.setSuccess(false);
        result.setData(data);
        result.setError(resultCode.message());
        return result;
    }

    public static ResponseData failure (String message)
    {
        ResponseData result = new ResponseData();
        result.setSuccess(false);
        result.setError(message);
        return result;
    }
}
