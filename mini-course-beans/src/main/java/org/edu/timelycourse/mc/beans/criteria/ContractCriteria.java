package org.edu.timelycourse.mc.beans.criteria;

import lombok.Data;
import org.edu.timelycourse.mc.common.utils.StringUtils;

/**
 * Created by x36zhao on 2018/4/20.
 */
@Data
public class ContractCriteria extends BaseCriteria
{
    private Integer id;
    private String studentName;
    private String consultantName;
    private String supervisorName;
    private Integer contractStatus;
    private Integer payStatus;
    private Integer enrollType;
    private Double remainedPeriod;
    private Integer arrangeStatus;

    public String getStudentName ()
    {
       return StringUtils.decodeURLText(studentName);
    }

    public String getConsultantName ()
    {
        return StringUtils.decodeURLText(consultantName);
    }

    public String getSupervisorName ()
    {
        return StringUtils.decodeURLText(supervisorName);
    }
}
