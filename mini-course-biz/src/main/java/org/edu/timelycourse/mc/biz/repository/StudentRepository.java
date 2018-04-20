package org.edu.timelycourse.mc.biz.repository;

import org.apache.ibatis.annotations.Param;
import org.edu.timelycourse.mc.beans.model.StudentModel;
import org.springframework.stereotype.Component;

/**
 * Created by x36zhao on 2018/3/31.
 */
@Component
public interface StudentRepository extends BaseRepository<StudentModel>
{
    StudentModel getByWxId(@Param("wxId") String wxId);
}
