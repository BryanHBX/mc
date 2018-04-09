package org.edu.timelycourse.mc.biz.model.member;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.edu.timelycourse.mc.biz.model.BaseEntity;

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
    private String userName;
    private String password;
    private String phone;
    private Date lastLoginTime;
    private int userStatus;

    private String role;
    private List<UserRole> authorities;

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
            this.authorities.add(role);
        }
    }

    public void addRole (final UserRole userRole)
    {
        if (userRole != null)
        {
            if (authorities == null)
            {
                authorities = new ArrayList<UserRole>();
            }

            authorities.add(userRole);
        }
    }

}
