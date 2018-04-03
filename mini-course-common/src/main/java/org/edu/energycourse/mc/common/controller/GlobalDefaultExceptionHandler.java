package org.edu.energycourse.mc.common.controller;

import org.edu.energycourse.mc.common.constants.Constants;
import org.edu.energycourse.mc.common.entity.ResponseData;
import org.edu.energycourse.mc.common.exception.NoPermissionAccessPageException;
import org.edu.energycourse.mc.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Marco on 2018/3/31.
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler extends BaseController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    @ExceptionHandler(ServiceException.class)
    @org.springframework.web.bind.annotation.ResponseBody
    @ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseData<Map<String, Object>> handleServiceException(Exception ex)
    {
        LOGGER.error("Internal server error caught: " + ex);
        return createErrorQueryResult(ex);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @org.springframework.web.bind.annotation.ResponseBody
    @ResponseStatus( HttpStatus.UNAUTHORIZED)
    public ResponseData<Map<String, Object>> handleUnauthorizedException(HttpServletRequest req, Exception ex)
    {
        LOGGER.error("Access defined when requesting API - " + req.getRequestURI());
        return createErrorQueryResult(ex);
    }

    @ExceptionHandler(NoPermissionAccessPageException.class)
    public String handleNoPermissionAccessPageException(HttpServletRequest req, Exception ex)
    {
        LOGGER.error("No permission when accessing page - " + req.getRequestURI());
        return Constants.PAGE_UNAUTHORIZED;
    }

    @ExceptionHandler(Exception.class)
    @org.springframework.web.bind.annotation.ResponseBody
    @ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseData<Map<String, Object>> handleUnexpectedException(Exception ex)
    {
        LOGGER.error("Unexpected exception caught: " + ex);
        return createErrorQueryResult(ex);
    }

    protected String getModulePath ()
    {
        return null;
    }
}
