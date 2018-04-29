package org.edu.timelycourse.mc.beans.criteria;

import lombok.Data;

/**
 * Created by marco on 2018/4/29
 */
@Data
public class ContractArrangementCriteria extends BaseCriteria
{
    private Integer teacherId;
    private Integer contractId;
    private Integer clazzId;
}
