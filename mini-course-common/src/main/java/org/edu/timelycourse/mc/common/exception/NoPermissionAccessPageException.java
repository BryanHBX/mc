package org.edu.timelycourse.mc.common.exception;

/**
 * Created by Marco on 2018/3/31.
 */
public class NoPermissionAccessPageException extends RuntimeException
{
    public NoPermissionAccessPageException(String message)
    {
        super(message);
    }

    public NoPermissionAccessPageException(Throwable e)
    {
        super(e);
    }

    public NoPermissionAccessPageException(String message, Exception e)
    {
        super(message, e);
    }

    public NoPermissionAccessPageException(String message, Throwable e)
    {
        super(message, e);
    }
}
