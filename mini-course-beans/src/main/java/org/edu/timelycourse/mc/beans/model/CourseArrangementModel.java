package org.edu.timelycourse.mc.beans.model;

import lombok.Data;
import org.edu.timelycourse.mc.common.utils.EntityUtils;

/**
 * Created by x36zhao on 2018/4/20.
 */
@Data
public class CourseArrangementModel extends BaseModel
{
    private Integer schoolId;
    private Integer contractId;
    private Integer teacherId;
    private Integer gradeId;

    @Override
    public boolean isValidInput ()
    {
        return EntityUtils.isValidEntityId(schoolId, contractId, teacherId) &&
                (gradeId == null || EntityUtils.isValidEntityId(gradeId));
    }
}
