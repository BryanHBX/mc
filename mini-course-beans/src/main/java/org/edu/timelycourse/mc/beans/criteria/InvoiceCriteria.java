package org.edu.timelycourse.mc.beans.criteria;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.edu.timelycourse.mc.common.utils.StringUtils;

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

    @JsonIgnore
    private Integer positiveFlag;

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
