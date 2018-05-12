package org.edu.timelycourse.mc.beans.criteria;

import lombok.Data;
import org.edu.timelycourse.mc.common.utils.StringUtils;

/**
 * Created by x36zhao on 2018/4/20.
 */
@Data
public class StudentCriteria extends BaseCriteria
{
    private String studentName;
    private String consultantName;
    private String supervisorName;
    private Integer studentStatus;
    private Integer grade;
    private Integer gradeSub;

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
