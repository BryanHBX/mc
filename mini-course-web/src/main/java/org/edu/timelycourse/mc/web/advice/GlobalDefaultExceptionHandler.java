package org.edu.timelycourse.mc.web.advice;

import io.jsonwebtoken.ExpiredJwtException;
import org.edu.timelycourse.mc.beans.entity.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseBody
    //@ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseData handleServiceException(HttpServletRequest req, Exception ex)
    {
        LOGGER.error("Internal server error when requesting API '{}' due to {}", req.getRequestURI(), ex);
        return ResponseData.failure(HttpStatus.INTERNAL_SERVER_ERROR.value(), "JWT Token is expired");
    }
}
