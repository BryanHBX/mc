package org.edu.timelycourse.mc.biz.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

/**
 * Created by Marco on 2018/3/31.
 */
@Data
@ToString(exclude = "id")
//ccc@JsonIgnoreProperties(value= { "id" } )
public class SystemRoleModel extends BaseEntity
{
    private String roleName;
    private String roleAlias;
    private Integer roleVisible;

    @Override
    public boolean isValidInput ()
    {
        return true;
    }
}
