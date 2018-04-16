package org.edu.timelycourse.mc.biz.security;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

/**
 * Created by x36zhao on 2018/4/9.
 */
@Getter
public class JwtAuthenticationResponse implements Serializable
{
    private final String token;

    public JwtAuthenticationResponse(String token)
    {
        this.token = token;
    }
}
