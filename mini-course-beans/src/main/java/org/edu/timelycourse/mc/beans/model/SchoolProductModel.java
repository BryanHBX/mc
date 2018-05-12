package org.edu.timelycourse.mc.beans.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.edu.timelycourse.mc.common.utils.StringUtils;
import org.edu.timelycourse.mc.common.utils.ValidatorUtils;

import java.util.Date;
import java.util.List;

/**
 * 学校产品
 */
@Data
public class SchoolProductModel extends BaseModel
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
    @JsonIgnore
    private Integer schoolId;

    /**
     * 创建时间
     */
    @JsonIgnore
    private Date creationTime;

    /**
     * 更新时间
     */
    @JsonIgnore
    private Date lastUpdateTime;

    /**
     * 子课程
     */
    private List<SchoolProductModel> children;

    public SchoolProductModel () {}

    public SchoolProductModel (String name, Integer parentId, Integer schoolId)
    {
        this.productName = name;
        this.parentId = parentId;
        this.schoolId = schoolId;
    }

    @Override
    public boolean isValidInput ()
    {
        return EntityUtils.isValidEntityId(schoolId) &&
                StringUtils.isNotEmpty(productName) &&
                (parentId == null ?
                        (EntityUtils.isValidEntityId(productType) && ValidatorUtils.isFloatNumber(productPeriod)) :
                        EntityUtils.isValidEntityId(parentId));
    }
}
