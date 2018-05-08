package org.edu.timelycourse.mc.beans.criteria;

import lombok.Data;

/**
 * Created by x36zhao on 2018/4/20.
 */
@Data
public class CourseAttendanceCriteria extends BaseCriteria
{
    private String startDate;
    private String endDate;
    private String teacherName;
    private String studentName;
    private Integer type;
}
