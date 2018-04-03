package org.edu.energycourse.mc.biz.service.member;

import org.edu.energycourse.mc.biz.entity.member.User;
import org.edu.energycourse.mc.biz.entity.member.UserRole;
import org.edu.energycourse.mc.biz.repository.BaseRepository;
import org.edu.energycourse.mc.biz.repository.member.UserRepository;
import org.edu.energycourse.mc.biz.repository.member.UserRoleRepository;
import org.edu.energycourse.mc.common.exception.ServiceException;
import org.edu.energycourse.mc.biz.service.BaseService;
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
