package org.edu.timelycourse.mc.beans.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.edu.timelycourse.mc.common.utils.EntityUtils;

/**
 * Created by x36zhao on 2018/4/20.
 */
@Data
public class CourseArrangementModel extends BaseModel
{
    @JsonIgnore
    private Integer schoolId;
    private Integer contractId;
    private Integer teacherId;
    private Integer classId;

    @Override
    @JsonIgnore
    public boolean isValidInput ()
    {
        return EntityUtils.isValidEntityId(schoolId, contractId, teacherId) &&
                (classId == null || EntityUtils.isValidEntityId(classId));
    }
}
