package org.edu.timelycourse.mc.biz.repository.school;

import org.apache.ibatis.annotations.Param;
import org.edu.timelycourse.mc.biz.entity.school.SchoolBasicInfo;
import org.edu.timelycourse.mc.biz.repository.BaseRepository;
import org.springframework.stereotype.Component;

/**
 * Created by x36zhao on 2018/3/31.
 */
@Component
public interface SchoolBasicInfoRepository extends BaseRepository<SchoolBasicInfo>
{
    SchoolBasicInfo getBySchoolName(@Param("name") String schoolName);
}
