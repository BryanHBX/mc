package org.edu.timelycourse.mc.beans.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 课程补课折算
 *
 * Created by marco on 2018/5/8
 */
@Data
public class ContractRemediationModel extends BaseModel
{
    /**
     * 课程大类
     */
    private Integer courseId;

    /**
     * 课程子类
     */
    private Integer subCourseId;

    /**
     * 折算课时
     */
    private double cost;

    /**
     * 教师ID
     */
    private Integer teacherId;

    /**
     * 学校ID
     */
    @JsonIgnore
    private Integer schoolId;

    /**
     * 合同ID
     */
    private Integer contractId;

    @Override
    public boolean isValidInput()
    {
        return false;
    }
}
