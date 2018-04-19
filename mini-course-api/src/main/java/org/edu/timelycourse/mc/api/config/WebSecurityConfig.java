package org.edu.timelycourse.mc.api.config;

import org.edu.timelycourse.mc.biz.filter.JwtAuthorizationTokenFilter;
import org.edu.timelycourse.mc.biz.security.JwtAuthenticationEntryPoint;
import org.edu.timelycourse.mc.biz.security.JwtTokenUtil;
import org.edu.timelycourse.mc.biz.security.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by x36zhao on 2017/3/3.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.route.authentication.path}")
    private String authenticationPath;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        // disable CSRF
        http.csrf().disable();

        // add unauthorized handler
        http
            .exceptionHandling()
            .authenticationEntryPoint(unauthorizedHandler);

        // disable session creation
        http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // un-secure swagger
        http
            .authorizeRequests()
            .antMatchers(
                    "/api/**/auth/**",
                    "/druid/**",
                    "/swagger-ui.html",
                    "/swagger-resources",
                    "/v2/**",
                    "/webjars/**",
                    "/images/**",
                    "/configuration/ui").permitAll()
            .anyRequest().authenticated();

        /*.and()
            .formLogin().successHandler(new RefererRedirectionAuthenticationSuccessHandler())
            .loginPage("/auth").permitAll().and()
            .logout()
            .logoutSuccessUrl("/").permitAll();
        */

        // add filter
        http.addFilterBefore(new JwtAuthorizationTokenFilter(
                userDetailsService(), jwtTokenUtil, tokenHeader),
                UsernamePasswordAuthenticationFilter.class
        );

        http
            .headers()
            .frameOptions().sameOrigin()
            .cacheControl();

        //http.httpBasic();
        //http.headers().frameOptions().sameOrigin();
    }

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        web
            .ignoring()
            .antMatchers(HttpMethod.POST, authenticationPath)

        .and()
            .ignoring()
            .antMatchers(
                HttpMethod.GET,
                "/",
                "/*.html",
                "/favicon.ico",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js",
                "/**/*.png",
                "/webjars/**",
                "/configuration/**",
                "/swagger-resources",
                "/v2/**", "/error"
            );

        web.ignoring().antMatchers(HttpMethod.OPTIONS);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
    {
        auth
                .userDetailsService(jwtUserDetailsService)
                .passwordEncoder(passwordEncoderBean());
    }

    @Bean
    public PasswordEncoder passwordEncoderBean()
    {
        return new BCryptPasswordEncoder();
    }
}
