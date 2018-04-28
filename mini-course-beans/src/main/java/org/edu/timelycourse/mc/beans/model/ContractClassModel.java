package org.edu.timelycourse.mc.beans.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * Created by x36zhao on 2018/4/20.
 */
@Data
public class ContractClassModel extends BaseModel
{
    @JsonIgnore
    private Integer schoolId;

    private String name;
    private Integer status;
    private Integer teacherId;

    @JsonIgnore
    private Date creationTime;

    @Override
    public boolean isValidInput ()
    {
        return false;
    }
}
