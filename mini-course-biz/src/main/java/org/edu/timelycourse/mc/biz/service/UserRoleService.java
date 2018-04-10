package org.edu.timelycourse.mc.biz.service;

import org.edu.timelycourse.mc.biz.model.UserRoleModel;
import org.edu.timelycourse.mc.biz.repository.UserRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRoleService extends BaseService<UserRoleModel>
{
    private static Logger LOGGER = LoggerFactory.getLogger(UserRoleService.class);

    @Autowired
    public UserRoleService(UserRoleRepository repository)
    {
        super(repository);
    }
}
