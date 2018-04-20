package org.edu.timelycourse.mc.beans.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by x36zhao on 2018/4/20.
 */
@Data
public class CourseAttendanceModel extends BaseModel
{
    /**
     * 学生ID
     */
    private Integer studentId;

    /**
     * 学校ID
     */
    private Integer schoolId;

    /**
     * 教师
     */
    private Integer teacherId;

    /**
     * 合同ID
     */
    private Integer contractId;

    /**
     * 考勤课时
     */
    private double cost;

    /**
     * 签到日期
     */
    private Date date;

    @Override
    public boolean isValidInput ()
    {
        return false;
    }
}
