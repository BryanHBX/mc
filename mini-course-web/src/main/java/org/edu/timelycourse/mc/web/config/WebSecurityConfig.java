package org.edu.timelycourse.mc.web.config;

import org.edu.timelycourse.mc.web.filter.AuthorizationFilter;
import org.edu.timelycourse.mc.web.security.AuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by x36zhao on 2017/3/3.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Autowired
    private AuthenticationEntryPoint unauthorizedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.csrf().disable();

        http
            .authorizeRequests()
            .antMatchers("/assets/**", "/plugins/**").permitAll()
            .anyRequest().authenticated().and()
            .formLogin()//.successHandler(new RefererRedirectionAuthenticationSuccessHandler())
            .loginPage("/login").permitAll();

        // add filter
        http.addFilterBefore(new AuthorizationFilter(tokenHeader, secret, expiration),
                UsernamePasswordAuthenticationFilter.class
        );

        // add unauthorized handler
        //http
        //    .exceptionHandling()
        //    .authenticationEntryPoint(unauthorizedHandler);

        //.and()
        //    .logout().logoutSuccessUrl("/logout").permitAll();

        //http.httpBasic();
        //http.headers().frameOptions().sameOrigin();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
    {
        //auth.authenticationProvider(authProvider);
    }
}
