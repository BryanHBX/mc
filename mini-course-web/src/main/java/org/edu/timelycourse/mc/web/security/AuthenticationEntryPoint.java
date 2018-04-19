package org.edu.timelycourse.mc.web.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by x36zhao on 2018/4/9.
 */
@Component
public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint, Serializable
{
    @Override
    public void commence (HttpServletRequest request,
                          HttpServletResponse response,
                          AuthenticationException e) throws IOException, ServletException
    {
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
