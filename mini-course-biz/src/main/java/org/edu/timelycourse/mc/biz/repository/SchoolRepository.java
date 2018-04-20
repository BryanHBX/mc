package org.edu.timelycourse.mc.biz.repository;

import org.apache.ibatis.annotations.Param;
import org.edu.timelycourse.mc.beans.model.SchoolModel;
import org.edu.timelycourse.mc.biz.repository.BaseRepository;
import org.springframework.stereotype.Component;

/**
 * Created by x36zhao on 2018/3/31.
 */
@Component
public interface SchoolRepository extends BaseRepository<SchoolModel>
{
    SchoolModel getBySchoolName(@Param("name") String schoolName);
}
