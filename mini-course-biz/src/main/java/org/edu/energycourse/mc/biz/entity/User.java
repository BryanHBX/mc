package org.edu.energycourse.mc.biz.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Marco on 2018/3/31.
 */
@Data
@ToString(exclude = "id")
@JsonIgnoreProperties(value = { "id", "userPassword", "role", "roles"})
public class User extends BaseEntity
{
    private String userId;
    //private String userDisplayName;
    //private String userMail;
    //private String userPassword;
    private Date lastLoginTime;

    private String role;
    private List<UserRole> roles;

    public User()
    {
    }

    public User(User user)
    {
        this(user, null);
    }

    public User(User user, UserRole role)
    {
        this.userId = user.getUserId();

        if (role != null)
        {
            this.roles.add(role);
        }
    }

    public void addRole (final UserRole userRole)
    {
        if (userRole != null)
        {
            if (roles == null)
            {
                roles = new ArrayList<UserRole>();
            }

            roles.add(userRole);
        }
    }

}
