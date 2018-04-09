package org.edu.timelycourse.mc.biz.security;

import org.edu.timelycourse.mc.biz.model.member.User;
import org.edu.timelycourse.mc.biz.model.member.UserRole;
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

    public static JwtUser create(User user)
    {
        return new JwtUser (
                user.getId(),
                user.getUserName(),
                user.getPassword(),
                user.getUserId(),
                user.getPhone(),
                mapToGrantedAuthorities(user.getAuthorities())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<UserRole> authorities)
    {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
    }
}
