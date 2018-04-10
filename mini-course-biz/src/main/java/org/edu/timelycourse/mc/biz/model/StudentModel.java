package org.edu.timelycourse.mc.biz.model;

import org.edu.timelycourse.mc.biz.model.BaseEntity;

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
     * 学生ID
     */
    private Integer studentId;

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

    @Override
    public boolean isValid()
    {
        return true;
    }
}
