package org.edu.timelycourse.mc.beans.model;

import lombok.Data;
import lombok.ToString;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.edu.timelycourse.mc.common.utils.StringUtils;

import java.util.List;

/**
 * 系统配置
 *
 * Created by x36zhao on 2017/3/16.
 */
@Data
@ToString(exclude = "id")
public class SystemConfigModel extends BaseModel
{
    /**
     * 配置项ID
     */
    private String configName;

    /**
     * 配置项名
     */
    private String configDescription;

    /**
     * 父配置项ID
     */
    private Integer parentId;

    /**
     * 单配置项, 不支持添加子节点
     */
    private int single;

    /**
     * 子配置项
     */
    private List<SystemConfigModel> children;

    public SystemConfigModel () {}

    public SystemConfigModel (String name, String desc, Integer parentId)
    {
        this.configName = name;
        this.parentId = parentId;
        this.configDescription = desc;
    }

    public boolean hasMultipleValue ()
    {
        return single > 0;
    }

    @Override
    public boolean isValidInput ()
    {
        return StringUtils.isNotEmpty(configName, configDescription) &&
                (parentId == null || EntityUtils.isValidEntityId(parentId));
    }
}
