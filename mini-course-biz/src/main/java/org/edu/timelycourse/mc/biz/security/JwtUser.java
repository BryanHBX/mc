package org.edu.timelycourse.mc.biz.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by x36zhao on 2018/4/9.
 */
@Getter
public class JwtUser implements UserDetails
{
    private Integer id;
    private String userName;
    private String password;
    private String userId;
    private String phone;

    private final Collection<? extends GrantedAuthority> authorities;

    public JwtUser ( Integer id, String username, String password, String userId, String phone,
                     Collection<? extends GrantedAuthority> authorities)
    {
        this.id = id;
        this.userName = username;
        this.password = password;
        this.userId = userId;
        this.phone = phone;
        this.authorities = authorities;
    }

    @JsonIgnore
    public Integer getId()
    {
        return id;
    }

    @JsonIgnore
    public String getPassword ()
    {
        return password;
    }

    public String getUsername ()
    {
        return userName;
    }

    public boolean isAccountNonExpired ()
    {
        return true;
    }

    public boolean isAccountNonLocked ()
    {
        return true;
    }

    public boolean isCredentialsNonExpired ()
    {
        return true;
    }

    public boolean isEnabled ()
    {
        return true;
    }
}
