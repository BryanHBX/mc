package org.edu.timelycourse.mc.beans.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.edu.timelycourse.mc.beans.paging.PagingBean;
import org.edu.timelycourse.mc.common.utils.StringUtil;

/**
 * Created by marco on 2018/4/27
 */
@Data
@JsonIgnoreProperties(value = { "schoolId" })
public class ContractArrangementDTO extends BaseDTO
{
    private NamedOptionProperty teacher;
    private NamedOptionProperty clazz;

    @JsonIgnore
    private Integer contractId;

    private Integer absenceCost;

    @Override
    @JsonIgnore
    public boolean isValid()
    {
        return teacher != null && teacher.getId() != null;
    }
}
