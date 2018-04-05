package org.edu.timelycourse.mc.biz.repository.member;

import org.edu.timelycourse.mc.biz.entity.member.User;
import org.apache.ibatis.annotations.Param;
import org.edu.timelycourse.mc.biz.repository.BaseRepository;
import org.springframework.stereotype.Component;

/**
 * Created by x36zhao on 2018/3/31.
 */
@Component
public interface UserRepository extends BaseRepository<User>
{
    User getByUserName (@Param("name") String userName);
    User getByUserId (@Param("uid") String userId);
}
