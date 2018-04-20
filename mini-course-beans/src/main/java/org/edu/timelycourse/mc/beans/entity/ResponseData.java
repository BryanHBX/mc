package org.edu.timelycourse.mc.beans.entity;

import lombok.Data;
import org.edu.timelycourse.mc.beans.enums.EResultCode;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * Created by Marco on 2018/3/31.
 */
@Data
public class ResponseData<T> implements Serializable
{
    private int statusCode;
    private boolean success;
    private T data;
    private String error;

    public ResponseData () {}

    public static ResponseData success ()
    {
        return success(null);
    }

    public static <T> ResponseData success (T data)
    {
        ResponseData<T> result = new ResponseData<T>();
        result.setData(data);
        result.setSuccess(true);
        result.setStatusCode(HttpStatus.OK.value());
        return result;
    }

    public static ResponseData failure (EResultCode resultCode)
    {
        return failure(resultCode, null);
    }

    public static <T> ResponseData failure (EResultCode resultCode, T data)
    {
        ResponseData<T> result = new ResponseData<T>();
        result.setSuccess(false);
        result.setData(data);
        result.setError(resultCode.message());
        return result;
    }

    public static ResponseData failure (int statusCode, String message)
    {
        ResponseData result = new ResponseData();
        result.setSuccess(false);
        result.setStatusCode(statusCode);
        result.setError(message);
        return result;
    }
}
