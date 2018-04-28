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

    /**
     * 学校ID
     */
    @JsonIgnore
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
        return EntityUtils.isValidEntityId(schoolId, studentId, teacherId, contractId)
                && cost > 0 && date != null;
    }
}
