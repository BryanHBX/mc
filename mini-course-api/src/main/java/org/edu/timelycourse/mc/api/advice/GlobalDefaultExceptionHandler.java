package org.edu.timelycourse.mc.api.advice;

import org.edu.timelycourse.mc.biz.utils.LocaleMessageSource;
import org.edu.timelycourse.mc.common.controller.BaseController;
import org.edu.timelycourse.mc.common.entity.ResponseData;
import org.edu.timelycourse.mc.common.exception.AuthenticationException;
import org.edu.timelycourse.mc.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Marco on 2018/3/31.
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler extends ResponseEntityExceptionHandler
{
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    @Autowired
    private LocaleMessageSource messageSource;

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    //@ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseData handleServiceException(HttpServletRequest req, Exception ex)
    {
        LOGGER.error("Internal server error when requesting API '{}' due to {}", req.getRequestURI(), ex);
        return ResponseData.failure(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                messageSource.getMessage("http.error.server"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    //@ResponseStatus( HttpStatus.FORBIDDEN)
    public ResponseData handleUnauthorizedException(HttpServletRequest req, Exception ex)
    {
        LOGGER.error("Access defined when requesting API '{}' due to {}", req.getRequestURI(), ex);
        return ResponseData.failure(HttpStatus.FORBIDDEN.value(),
                messageSource.getMessage("http.error.forbidden"));
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    //@ResponseStatus( HttpStatus.UNAUTHORIZED)
    public ResponseData handleAuthenticationException(HttpServletRequest req, Exception ex)
    {
        LOGGER.error("Authentication fails when requesting API '{}' due to {}", req.getRequestURI(), ex);
        return ResponseData.failure(HttpStatus.UNAUTHORIZED.value(),
                messageSource.getMessage("http.error.unauthorized"));
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    //@ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseData handleUnexpectedException(HttpServletRequest req, Exception ex)
    {
        LOGGER.error("Unexpected exception when requesting API '{}' due to {}", req.getRequestURI(), ex);
        return ResponseData.failure(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

    protected String getModulePath ()
    {
        return null;
    }
}
