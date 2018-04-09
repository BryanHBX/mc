package org.edu.timelycourse.mc.biz.repository.member;

import org.edu.timelycourse.mc.biz.model.member.User;
import org.apache.ibatis.annotations.Param;
import org.edu.timelycourse.mc.biz.repository.BaseRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by x36zhao on 2018/3/31.
 */
@Component
public interface UserRepository extends BaseRepository<User>
{
    User getByUserPhone (@Param("phone") String userPhone);
    User getByUserId (@Param("identity") String userIdentity);
    List<User> getBySchoolId (@Param("sid") Integer schoolId);
}
