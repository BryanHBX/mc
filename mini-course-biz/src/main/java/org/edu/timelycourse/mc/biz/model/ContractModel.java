package org.edu.timelycourse.mc.biz.model;

import lombok.Data;
import org.edu.timelycourse.mc.biz.model.BaseEntity;

import java.util.Date;

@Data
public class ContractModel extends BaseEntity
{
    /**
     * 合同编号
     */
    private String contractNo;

    /**
     * 咨询师ID
     */
    private Integer consultantId;

    /**
     * 报名类型
     */
    private Integer enrollType;

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

    /**
     * 报名课时
     */
    private double enrollPeriod;

    /**
     * 赠送课时
     */
    private double freePeriod;

    /**
     * 签约总价
     */
    private double contractPrice;

    /**
     * 优惠金额
     */
    private double discountPrice;

    /**
     * 实收金额
     */
    private double totalPrice;

    /**
     * 合同签约日期
     */
    private Date contractDate;

    /**
     * 合同创建时间
     */
    private Date creationTime;

    /**
     * 合同更新时间
     */
    private Date lastUpdateTime;

    /**
     * 学生信息
     */
    private StudentModel student;

    @Override
    public boolean isValid()
    {
        return true;
    }
}
