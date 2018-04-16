package org.edu.timelycourse.mc.biz.security;

import org.edu.timelycourse.mc.biz.model.UserModel;
import org.edu.timelycourse.mc.biz.model.UserRoleModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;
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
                user.getSchoolId(),
                user.getUserName(),
                user.getPassword(),
                user.getUserIdentity(),
                user.getPhone(),
                mapToGrantedAuthorities(user.getAuthorities())
        );
    }

    public static JwtUser create (Integer userId,
                                  Integer schoolId,
                                  String userName,
                                  String userIdCard,
                                  String mobile,
                                  Collection<? extends GrantedAuthority> authorities)
    {
        return new JwtUser(userId, schoolId, userName, userIdCard, mobile, authorities);
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<UserRoleModel> authorities)
    {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole()))
                .collect(Collectors.toList());
    }
}
