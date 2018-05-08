package org.edu.timelycourse.mc.beans.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.edu.timelycourse.mc.common.utils.EntityUtils;

import java.util.Date;

/**
 * Created by x36zhao on 2018/4/20.
 */
@Data
public class ContractAttendanceModel extends BaseModel
{
    /**
     * 学生ID
     */
    private Integer studentId;

    @JsonIgnore
    private StudentModel student;

    /**
     * 学校ID
     */
    @JsonIgnore
    private Integer schoolId;

    /**
     * 教师
     */
    private Integer teacherId;

    @JsonIgnore
    private UserModel teacher;

    /**
     * 合同ID
     */
    private Integer contractId;

    @JsonIgnore
    private ContractModel contract;

    /**
     * 考勤课时
     */
    private double cost;

    /**
     * 签到日期
     */
    private Date date;

    /**
     * 考勤状态
     */
    private Integer status;

    /**
     * 考勤类型
     */
    private Integer type;

    /**
     * 补课登记
     */
    private Integer refId;

    //@JsonIgnore
    //private ContractAttendanceModel ref;

    /**
     * 签名图片路径
     */
    private String signPath;

    @Override
    public boolean isValidInput ()
    {
        return EntityUtils.isValidEntityId(schoolId, studentId, teacherId, contractId)
                && cost > 0 && date != null;
    }

    @JsonIgnore
    public String getDescription()
    {
        StringBuilder builder = new StringBuilder();
        return builder.toString();
    }
}
