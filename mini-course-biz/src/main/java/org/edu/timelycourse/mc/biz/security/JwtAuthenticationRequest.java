package org.edu.timelycourse.mc.biz.security;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by x36zhao on 2018/4/9.
 */
@Data
public class JwtAuthenticationRequest implements Serializable
{
    private String username;
    private String password;

    public JwtAuthenticationRequest () {}

    public JwtAuthenticationRequest (String username, String password)
    {
        this.username = username;
        this.password = password;
    }
}
