package org.edu.timelycourse.mc.biz.model;

import lombok.Data;
import org.edu.timelycourse.mc.biz.enums.EContactType;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.edu.timelycourse.mc.common.utils.StringUtil;

import java.util.Date;

@Data
public class StudentModel extends BaseEntity
{
    /**
     * 学生姓名
     */
    private String name;

    /**
     * 微信号
     */
    private String wxId;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人电话
     */
    private String contactPhone;

    /**
     * 其他备注
     */
    private String meto;

    /**
     * 联系人关系
     */
    private Integer contactRelationType;

    /**
     * 咨询师
     */
    private Integer consultantId;

    /**
     * 学生年段
     */
    private Integer levelId;

    /**
     * 细分年段
     */
    private Integer subLevelId;

    /**
     * 课程名称
     */
    private Integer courseId;

    /**
     * 课程子类
     */
    private Integer subCourseId;

    /**
     * 创建时间
     */
    private Date creationTime;

    /**
     * 更新时间
     */
    private Date lastUpdateTime;

    /**
     * 学校ID
     */
    private Integer schoolId;

    @Override
    public boolean isValidInput ()
    {
        return EContactType.hasValue(this.contactRelationType) &&
                StringUtil.isNotEmpty(wxId, contactName, contactPhone, name) &&
                EntityUtils.isValidEntityId(schoolId, levelId, subLevelId, courseId, subCourseId);
    }

    @Override
    public String getUrlParams()
    {
        return null;
    }
}
