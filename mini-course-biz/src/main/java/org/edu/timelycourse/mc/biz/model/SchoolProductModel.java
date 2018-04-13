package org.edu.timelycourse.mc.biz.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SchoolProductModel extends BaseEntity
{
    /**
     * 课程名称
     */
    private String productName;

    /**
     * 课时
     */
    private Double productPeriod;

    /**
     * 课程类别
     */
    private Integer productType;

    /**
     * 父课程
     */
    private Integer parentId;

    /**
     * 学校ID
     */
    private Integer schoolId;

    /**
     * 创建时间
     */
    private Date creationTime;

    /**
     * 更新时间
     */
    private Date lastUpdateTime;

    /**
     * 子课程
     */
    private List<SchoolProductModel> children;

    @Override
    public boolean isValidInput ()
    {
        return false;
    }
}
