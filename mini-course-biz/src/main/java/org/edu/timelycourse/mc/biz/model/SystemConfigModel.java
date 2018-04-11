package org.edu.timelycourse.mc.biz.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by x36zhao on 2017/3/16.
 */
@Data
@ToString(exclude = "id")
public class SystemConfigModel extends BaseEntity
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

    public boolean hasMultipleValue ()
    {
        return single > 0;
    }

    @Override
    public boolean isValidInput ()
    {
        return true;
    }
}
