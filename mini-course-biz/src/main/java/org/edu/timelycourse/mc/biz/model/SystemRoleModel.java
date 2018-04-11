package org.edu.timelycourse.mc.biz.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

/**
 * Created by Marco on 2018/3/31.
 */
@Data
@ToString(exclude = "id")
@JsonIgnoreProperties(value= { "id" } )
public class SystemRoleModel extends BaseEntity
{
    private String roleName;
    private String roleAlias;

    @Override
    public boolean isValidInput ()
    {
        return true;
    }
}
