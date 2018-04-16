package org.edu.timelycourse.mc.biz.security;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by x36zhao on 2018/4/16.
 */
@Data
public class JwtAuthorityName implements Serializable
{
    private String authority;
    public JwtAuthorityName () {}
    public JwtAuthorityName (String name) {this.authority = name;}
}
