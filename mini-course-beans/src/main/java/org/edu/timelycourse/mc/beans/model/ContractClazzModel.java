package org.edu.timelycourse.mc.beans.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.edu.timelycourse.mc.common.utils.StringUtils;

import java.util.Date;

/**
 * 班课
 *
 * Created by x36zhao on 2018/4/20.
 */
@Data
public class ContractClazzModel extends BaseModel
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
        return EntityUtils.isValidEntityId(schoolId, teacherId) &&
                StringUtils.isNotEmpty(name);
    }
}
