package org.edu.timelycourse.mc.beans.criteria;

import lombok.Data;

/**
 * Created by x36zhao on 2018/4/20.
 */
@Data
public class ContractCriteria extends BaseCriteria
{
    private String studentName;
    private String consultantName;
    private String supervisorName;
    private Integer contractStatus;
    private Integer payStatus;
    private Integer enrollType;
}
