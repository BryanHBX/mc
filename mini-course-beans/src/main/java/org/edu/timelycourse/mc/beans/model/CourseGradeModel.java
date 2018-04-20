package org.edu.timelycourse.mc.beans.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by x36zhao on 2018/4/20.
 */
@Data
public class CourseGradeModel extends BaseModel
{
    private String name;
    private Integer status;
    private Integer schoolId;
    private Date creationTime;

    @Override
    public boolean isValidInput ()
    {
        return false;
    }
}
