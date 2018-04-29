package org.edu.timelycourse.mc.beans.criteria;

import lombok.Data;

/**
 * Created by marco on 2018/4/29
 */
@Data
public class ContractClazzCriteria extends BaseCriteria
{
    private Integer teacherId;
    private String clazzName;

    public ContractClazzCriteria () {}
    public ContractClazzCriteria (String name, Integer teacherId, Integer schoolId)
    {
        this.setTeacherId(teacherId);
        this.setClazzName(name);
        this.setSchoolId(schoolId);
    }
}
