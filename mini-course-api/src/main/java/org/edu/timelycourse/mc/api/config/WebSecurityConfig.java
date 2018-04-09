package org.edu.timelycourse.mc.api.config;

import org.edu.timelycourse.mc.common.security.RefererRedirectionAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by x36zhao on 2017/3/3.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.csrf().disable();

        http
            .authorizeRequests()
            .antMatchers("/assets/**", "/plugins/**", "/**").permitAll()
            .anyRequest().authenticated().and()
            .formLogin().successHandler(new RefererRedirectionAuthenticationSuccessHandler())
            .loginPage("/login").permitAll().and()
            .logout()
            .logoutSuccessUrl("/").permitAll();

        //http.httpBasic();
        //http.headers().frameOptions().sameOrigin();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
    {
        //auth.authenticationProvider(authProvider);
    }
}
