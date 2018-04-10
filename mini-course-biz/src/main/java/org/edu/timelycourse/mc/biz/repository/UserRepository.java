package org.edu.timelycourse.mc.biz.repository;

import org.edu.timelycourse.mc.biz.model.UserModel;
import org.apache.ibatis.annotations.Param;
import org.edu.timelycourse.mc.biz.repository.BaseRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by x36zhao on 2018/3/31.
 */
@Component
public interface UserRepository extends BaseRepository<UserModel>
{
    UserModel getByUserPhone (@Param("phone") String userPhone);
    UserModel getByUserId (@Param("identity") String userIdentity);
    List<UserModel> getBySchoolId (@Param("sid") Integer schoolId);
}
