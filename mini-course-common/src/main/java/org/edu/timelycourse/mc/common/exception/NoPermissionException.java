package org.edu.timelycourse.mc.common.exception;

/**
 * Created by Marco on 2018/3/31.
 */
public class NoPermissionException extends RuntimeException
{
    public NoPermissionException (String message)
    {
        super(message);
    }

    public NoPermissionException (Throwable e)
    {
        super(e);
    }

    public NoPermissionException (String message, Exception e)
    {
        super(message, e);
    }

    public NoPermissionException (String message, Throwable e)
    {
        super(message, e);
    }
}
