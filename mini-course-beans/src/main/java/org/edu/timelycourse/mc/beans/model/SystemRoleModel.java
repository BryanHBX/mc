package org.edu.timelycourse.mc.beans.model;

import lombok.Data;
import lombok.ToString;
import org.edu.timelycourse.mc.beans.enums.EVisible;
import org.edu.timelycourse.mc.common.utils.StringUtils;

/**
 * 系统角色权限
 *
 * Created by Marco on 2018/3/31.
 */
@Data
@ToString(exclude = "id")
public class SystemRoleModel extends BaseModel
{
    private String roleName;
    private String roleAlias;
    private Integer roleVisible;

    @Override
    public boolean isValidInput ()
    {
        return StringUtils.isNotEmpty(roleName, roleAlias) &&
                EVisible.hasValue(roleVisible);
    }
}
