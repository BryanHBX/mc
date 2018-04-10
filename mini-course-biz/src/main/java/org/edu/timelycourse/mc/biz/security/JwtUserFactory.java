package org.edu.timelycourse.mc.biz.security;

import org.edu.timelycourse.mc.biz.model.UserModel;
import org.edu.timelycourse.mc.biz.model.UserRoleModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by x36zhao on 2018/4/9.
 */
public final class JwtUserFactory
{
    private JwtUserFactory()
    {
    }

    public static JwtUser create(UserModel user)
    {
        return new JwtUser (
                user.getId(),
                user.getUserName(),
                user.getPassword(),
                user.getUserIdentity(),
                user.getPhone(),
                mapToGrantedAuthorities(user.getAuthorities())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<UserRoleModel> authorities)
    {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
    }
}
