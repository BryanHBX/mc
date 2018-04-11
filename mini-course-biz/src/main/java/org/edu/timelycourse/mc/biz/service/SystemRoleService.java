package org.edu.timelycourse.mc.biz.service;

import org.edu.timelycourse.mc.biz.model.SystemRoleModel;
import org.edu.timelycourse.mc.biz.repository.SystemRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class SystemRoleService extends BaseService<SystemRoleModel>
{
    private static Logger LOGGER = LoggerFactory.getLogger(SystemRoleService.class);

    private SystemRoleRepository roleRepository;

    @Autowired
    public SystemRoleService(SystemRoleRepository repository)
    {
        super(repository);
        this.roleRepository = repository;
    }

    public SystemRoleModel findByAlias (String alias)
    {
        return roleRepository.getByAlias(alias);
    }
}
