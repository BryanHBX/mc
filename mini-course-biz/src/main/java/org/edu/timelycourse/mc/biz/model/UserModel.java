package org.edu.timelycourse.mc.biz.model;

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
@JsonIgnoreProperties(value = { "id", "password" })
public class UserModel extends BaseEntity
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

    private List<UserRoleModel> authorities;

    public UserModel()
    {
    }

    public UserModel(UserModel user)
    {
        this(user, null);
    }

    public UserModel(UserModel user, UserRoleModel role)
    {
        this.userIdentity = user.getUserIdentity();

        if (role != null)
        {
            this.authorities.add(role);
        }
    }

    public void addRole (final UserRoleModel userRole)
    {
        if (userRole != null)
        {
            if (authorities == null)
            {
                authorities = new ArrayList<UserRoleModel>();
            }

            authorities.add(userRole);
        }
    }

    @Override
    public boolean isValidInput ()
    {
        return true;
    }
}
