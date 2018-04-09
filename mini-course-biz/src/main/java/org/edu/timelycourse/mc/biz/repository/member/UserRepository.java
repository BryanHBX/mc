package org.edu.timelycourse.mc.biz.repository.member;

import org.edu.timelycourse.mc.biz.model.member.User;
import org.apache.ibatis.annotations.Param;
import org.edu.timelycourse.mc.biz.repository.BaseRepository;
import org.springframework.stereotype.Component;

/**
 * Created by x36zhao on 2018/3/31.
 */
@Component
public interface UserRepository extends BaseRepository<User>
{
    User getByUsername (@Param("name") String username);
    User getByUserId (@Param("uid") String userId);
}
