package org.edu.timelycourse.mc.biz.model;

import lombok.Data;
import lombok.ToString;
import org.edu.timelycourse.mc.biz.enums.EVisible;
import org.edu.timelycourse.mc.common.utils.StringUtil;

/**
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
        return StringUtil.isNotEmpty(roleName, roleAlias) &&
                EVisible.hasValue(roleVisible);
    }

    @Override
    public String getUrlParams()
    {
        return null;
    }
}
