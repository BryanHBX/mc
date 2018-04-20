package org.edu.timelycourse.mc.beans.criteria;

import lombok.Data;

/**
 * Created by x36zhao on 2018/4/20.
 */
@Data
public class ContractInvoiceCriteria extends BaseCriteria
{
    private String startDate;
    private String endDate;
    private Integer contractStatus;
    private String studentName;
    private String consultantName;
    private String supervisorName;
    private Integer payType;
}
