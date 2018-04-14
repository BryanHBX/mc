package org.edu.timelycourse.mc.biz.model;

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
public class UserRoleModel extends BaseEntity
{
    private int userId;
    private String role;

    public UserRoleModel()
    {
    }

    public UserRoleModel(int userId, String authorityName)
    {
        this.userId = userId;
        this.role = authorityName;
    }

    @Override
    public boolean isValidInput ()
    {
        return true;
    }
}
