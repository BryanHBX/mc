package org.edu.timelycourse.mc.biz.model.member;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.edu.timelycourse.mc.biz.model.BaseEntity;

/**
 * Created by Marco on 2018/3/31.
 */
@Data
@ToString(exclude = "id")
@JsonIgnoreProperties(value= { "id", "userId" } )
public class UserRole extends BaseEntity
{
    private int userId;
    private String name;

    public UserRole()
    {
    }

    public UserRole(int userId, String authorityName)
    {
        this.userId = userId;
        this.name = authorityName;
    }
}
