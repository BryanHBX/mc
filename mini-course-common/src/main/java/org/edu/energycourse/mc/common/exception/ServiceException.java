package org.edu.energycourse.mc.common.exception;

/**
 * Created by Marco on 2018/3/31.
 */
public class ServiceException extends RuntimeException
{
    public ServiceException(String message)
    {
        super(message);
    }

    public ServiceException(Throwable e)
    {
        super(e);
    }

    public ServiceException(String message, Exception e)
    {
        super(message, e);
    }

    public ServiceException(String message, Throwable e)
    {
        super(message, e);
    }
}
