package org.edu.timelycourse.mc.beans.criteria;

import lombok.Data;
import org.edu.timelycourse.mc.common.utils.StringUtil;

/**
 * Created by x36zhao on 2018/4/20.
 */
@Data
public class InvoiceCriteria extends BaseCriteria
{
    private String startDate;
    private String endDate;
    private Integer status;
    private String studentName;
    private String consultantName;
    private String supervisorName;
    private Integer payType;

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
