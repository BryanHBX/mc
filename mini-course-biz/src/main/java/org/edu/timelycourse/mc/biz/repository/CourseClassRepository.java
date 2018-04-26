package org.edu.timelycourse.mc.biz.repository;

import org.apache.ibatis.annotations.Param;
import org.edu.timelycourse.mc.beans.model.CourseClassModel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by x36zhao on 2018/3/31.
 */
@Component
public interface CourseClassRepository extends BaseRepository<CourseClassModel>
{
    List<CourseClassModel> getByTeacher (@Param("tid") Integer teacherId,
                                         @Param("sid") Integer schoolId);
}
