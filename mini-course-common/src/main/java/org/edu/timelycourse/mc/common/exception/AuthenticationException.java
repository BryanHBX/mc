package org.edu.timelycourse.mc.common.exception;

/**
 * Created by x36zhao on 2018/4/16.
 */
public class AuthenticationException extends RuntimeException
{
    public AuthenticationException (String message, Throwable cause)
    {
        super(message, cause);
    }
}
