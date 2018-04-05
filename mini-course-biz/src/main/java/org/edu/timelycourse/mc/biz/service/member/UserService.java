package org.edu.timelycourse.mc.biz.service.member;

import org.edu.timelycourse.mc.biz.entity.member.User;
import org.edu.timelycourse.mc.biz.entity.member.UserRole;
import org.edu.timelycourse.mc.biz.repository.BaseRepository;
import org.edu.timelycourse.mc.biz.repository.member.UserRepository;
import org.edu.timelycourse.mc.biz.repository.member.UserRoleRepository;
import org.edu.timelycourse.mc.common.exception.ServiceException;
import org.edu.timelycourse.mc.biz.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by x36zhao on 2017/3/17.
 */
@Service
public class UserService extends BaseService<User>
{
    private static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository repository)
    {
        super(repository);
    }
}
