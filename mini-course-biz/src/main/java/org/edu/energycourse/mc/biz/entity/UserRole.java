package org.edu.energycourse.mc.biz.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

/**
 * Created by Marco on 2018/3/31.
 */
@Data
@ToString(exclude = "id")
@JsonIgnoreProperties(value= { "id", "userId" } )
public class UserRole extends BaseEntity
{
    private int userId;
    private String role;

    public UserRole()
    {
    }

    public UserRole(int userId, String role)
    {
        this.userId = userId;
        this.role = role;
    }
}
