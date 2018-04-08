package org.edu.timelycourse.mc.biz.repository.school;

import org.apache.ibatis.annotations.Param;
import org.edu.timelycourse.mc.biz.entity.school.SchoolInfo;
import org.edu.timelycourse.mc.biz.repository.BaseRepository;
import org.springframework.stereotype.Component;

/**
 * Created by x36zhao on 2018/3/31.
 */
@Component
public interface SchoolInfoRepository extends BaseRepository<SchoolInfo>
{
    SchoolInfo getBySchoolName(@Param("name") String schoolName);
}
