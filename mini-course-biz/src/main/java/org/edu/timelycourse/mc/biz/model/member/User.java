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
@JsonIgnoreProperties(value = { "id", "password", "role", "roles"})
public class User extends BaseEntity
{
    private String userIdentity;
    private String userName;
    private String password;
    private String phone;
    private String wxId;

    private int status;
    private int type;
    private int role;

    private int schoolId;

    private Date creationTime;
    private Date lastUpdateTime;
    private Date lastLoginTime;

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
        this.userIdentity = user.getUserIdentity();

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
