package org.edu.energycourse.mc.biz.service.member;

import org.edu.energycourse.mc.biz.entity.member.UserRole;
import org.edu.energycourse.mc.biz.repository.member.UserRoleRepository;
import org.edu.energycourse.mc.biz.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRoleService extends BaseService<UserRole>
{
    private static Logger LOGGER = LoggerFactory.getLogger(UserRoleService.class);

    @Autowired
    public UserRoleService(UserRoleRepository repository)
    {
        super(repository);
    }
}
