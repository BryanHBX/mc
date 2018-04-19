package org.edu.timelycourse.mc.web.filter;

import io.jsonwebtoken.ExpiredJwtException;
import org.edu.timelycourse.mc.biz.security.JwtTokenUtil;
import org.edu.timelycourse.mc.biz.security.JwtUser;
import org.edu.timelycourse.mc.biz.utils.SecurityContextHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
        catch (ExpiredJwtException ex)
        {
            LOGGER.warn("Expired JWT token", ex);
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        chain.doFilter(request, response);
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
