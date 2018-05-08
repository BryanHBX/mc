package org.edu.timelycourse.mc.biz.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by x36zhao on 2018/4/9.
 */
@Getter
@JsonDeserialize(using = JwtCustomUserDeserializer.class)
public class JwtUser implements UserDetails
{
    private Integer sid;
    private Integer uid;
    private Integer spf;
    private String password;

    private String idCard;
    private String phone;
    private String userName;

    @Setter
    @Getter
    private String token;

    private Collection<? extends GrantedAuthority> authorities;

    public JwtUser () {}

    public JwtUser ( Integer userId, Integer schoolId, Integer supervisorFlag, String username, String idCard, String phone,
                     Collection<? extends GrantedAuthority> authorities)
    {
        this(userId, schoolId, supervisorFlag, username, null, idCard, phone, authorities);
    }

    public JwtUser ( Integer userId, Integer schoolId, Integer supervisorFlag, String username, String password, String idCard, String phone,
                     Collection<? extends GrantedAuthority> authorities)
    {
        this.uid = userId;
        this.sid = schoolId;
        this.spf = supervisorFlag;
        this.userName = username;
        this.password = password;
        this.idCard = idCard;
        this.phone = phone;
        this.authorities = authorities;
    }

    public Integer getId()
    {
        return uid;
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
