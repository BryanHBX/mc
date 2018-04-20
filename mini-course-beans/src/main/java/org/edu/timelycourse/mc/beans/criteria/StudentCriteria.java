package org.edu.timelycourse.mc.beans.criteria;

import lombok.Data;
import org.edu.timelycourse.mc.common.utils.StringUtil;

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
        return StringUtil.decodeURLText(studentName);
    }

    public String getConsultantName ()
    {
        return StringUtil.decodeURLText(consultantName);
    }

    public String getSupervisorName ()
    {
        return StringUtil.decodeURLText(supervisorName);
    }
}
