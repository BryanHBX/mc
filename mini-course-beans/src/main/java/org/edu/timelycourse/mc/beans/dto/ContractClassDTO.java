package org.edu.timelycourse.mc.beans.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * Created by x36zhao on 2018/4/26.
 */
@Data
public class ContractClassDTO extends BaseDTO
{
    private Integer contractId;
    private Integer teacherId;
    private String name;

    @JsonIgnore
    private Integer schoolId;

    @Override
    @JsonIgnore
    public boolean isValid ()
    {
        return false;
    }
}
