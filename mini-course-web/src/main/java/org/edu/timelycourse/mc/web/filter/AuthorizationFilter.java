package org.edu.timelycourse.mc.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.edu.timelycourse.mc.biz.security.JwtTokenUtil;
import org.edu.timelycourse.mc.biz.security.JwtUser;
import org.edu.timelycourse.mc.biz.utils.SecurityContextHelper;
import org.edu.timelycourse.mc.beans.entity.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationFilter extends OncePerRequestFilter
{
    private Logger LOGGER = LoggerFactory.getLogger(AuthorizationFilter.class);

    private static String TOKEN_REQUEST_HEADER = "Bearer ";
    private static String TOKEN_REQUEST_PARAMETER = "token";
    private static String AJAX_REQUEST_HEADER = "Ajax";

    private JwtTokenUtil jwtTokenUtil;
    private String tokenHeader;

    public AuthorizationFilter (String tokenHeader, String secret, Long expiration)
    {
        this.jwtTokenUtil = new JwtTokenUtil(secret, expiration);
        this.tokenHeader = tokenHeader;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("processing authentication for '{}'", request.getRequestURL());
        }

        try
        {
            String authToken = getToken(request);
            JwtUser userClaims = null;
            if (authToken != null)
            {
                userClaims = jwtTokenUtil.getUserDetailsFromToken(authToken);
            }

            if (userClaims != null)
            {
                if (!jwtTokenUtil.isTokenExpired(authToken))
                {
                    LOGGER.info("authorized user '{}', setting security context", userClaims);
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userClaims, null, userClaims.getAuthorities());

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                else
                {
                    SecurityContextHolder.clearContext();
                }
            }
            else
            {
                // for normal page refresh
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                if (auth == null || auth instanceof AnonymousAuthenticationToken)
                {
                    // do nothing in case not logon
                }
                else
                {
                    JwtUser jwtUser = SecurityContextHelper.getPrincipal();
                    if (jwtTokenUtil.isTokenExpired(jwtUser.getToken()))
                    {
                        SecurityContextHolder.getContext().setAuthentication(null);
                    }
                }
            }
        }
        catch (ExpiredJwtException | AccessDeniedException ex)
        {
            LOGGER.warn("Access is denied", ex);
            SecurityContextHolder.getContext().setAuthentication(null);
            if (isAjaxRequest(request))
            {
                response.getOutputStream().write(restResponseBytes(
                        ResponseData.failure(HttpStatus.FORBIDDEN.value(), "Unauthorized")));
                return;
            }
        }
//        catch (AccessDeniedException ex)
//        {
//            LOGGER.warn("Access denied", ex);
//            SecurityContextHolder.getContext().setAuthentication(null);
//
//            if (isAjaxRequest(request))
//            {
//                response.getOutputStream().write(restResponseBytes(
//                        ResponseData.failure(HttpStatus.FORBIDDEN.value(), "Unauthorized")));
//                return;
//            }
//        }

        chain.doFilter(request, response);
    }

    private byte[] restResponseBytes(ResponseData responseData) throws IOException
    {
        String serialized = new ObjectMapper().writeValueAsString(responseData);
        return serialized.getBytes();
    }

    private boolean isAjaxRequest (HttpServletRequest request)
    {
        return request.getHeader(AJAX_REQUEST_HEADER) != null;
    }

    private String getToken (HttpServletRequest request)
    {
        final String requestHeader = request.getHeader(this.tokenHeader);
        if (requestHeader != null && requestHeader.startsWith(TOKEN_REQUEST_HEADER))
        {
            return requestHeader.substring(TOKEN_REQUEST_HEADER.length());
        }

        if (request.getParameter(TOKEN_REQUEST_PARAMETER) != null)
        {
            return request.getParameter(TOKEN_REQUEST_PARAMETER);
        }

        return null;
    }
}
