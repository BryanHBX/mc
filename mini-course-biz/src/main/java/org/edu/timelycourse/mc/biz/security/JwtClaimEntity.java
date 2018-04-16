package org.edu.timelycourse.mc.biz.security;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by x36zhao on 2018/4/16.
 */
@Data
public class JwtClaimEntity implements Serializable
{
    /**
     * 学校ID
     */
    private Integer sid;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 用户ID
     */
    private Integer uid;

    /**
     * 用户权限
     */
    private List<String> authorities;
}
