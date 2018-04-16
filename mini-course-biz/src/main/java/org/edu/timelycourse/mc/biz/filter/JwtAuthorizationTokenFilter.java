package org.edu.timelycourse.mc.biz.filter;

import io.jsonwebtoken.ExpiredJwtException;
import org.edu.timelycourse.mc.biz.security.JwtTokenUtil;
import org.edu.timelycourse.mc.biz.security.JwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by x36zhao on 2018/4/9.
 */
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter
{
    private Logger LOGGER = LoggerFactory.getLogger(JwtAuthorizationTokenFilter.class);

    private String tokenHeader;
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;

    private static String TOKEN_REQUEST_HEADER = "Bearer ";

    public JwtAuthorizationTokenFilter (UserDetailsService userDetailsService,
                                        JwtTokenUtil jwtTokenUtil,
                                        String tokenHeader)
    {
        this.tokenHeader = tokenHeader;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal (HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain chain) throws ServletException, IOException
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("processing authentication for '{}'", request.getRequestURL());

        final String requestHeader = request.getHeader(this.tokenHeader);

        JwtUser userClaims = null;
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith(TOKEN_REQUEST_HEADER))
        {
            authToken = requestHeader.substring(TOKEN_REQUEST_HEADER.length());
            try
            {
                userClaims = jwtTokenUtil.getUserDetailsFromToken(authToken);
            }
            catch (IllegalArgumentException e)
            {
                LOGGER.error("an error occured during getting username from token", e);
            }
            catch (ExpiredJwtException e)
            {
                LOGGER.warn("the token is expired and not valid anymore", e);
            }
        }
        else
        {
            LOGGER.warn("couldn't find bearer string, will ignore the header");
        }

        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("checking authentication for user '{}'", userClaims.getPhone());
        }

        if (userClaims != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            if (LOGGER.isDebugEnabled())
            {
                LOGGER.debug("security context was null, so authorizing user");
            }

            JwtUser userDetails = (JwtUser) userDetailsService.loadUserByUsername(userClaims.getPhone());
            if (jwtTokenUtil.validateToken(authToken, userDetails))
            {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                LOGGER.info("authorized user '{}', setting security context", userClaims);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }
}
