package org.edu.timelycourse.mc.biz.service;

import org.edu.timelycourse.mc.beans.model.SystemRoleModel;
import org.edu.timelycourse.mc.biz.repository.SystemRoleRepository;
import org.edu.timelycourse.mc.biz.utils.Asserts;
import org.edu.timelycourse.mc.common.exception.ServiceException;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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

    @Override
    public SystemRoleModel add(SystemRoleModel entity)
    {
        if (entity.isValidInput())
        {
            // check if role alias exists or not
            if (roleRepository.getByAlias(entity.getRoleAlias()) != null)
            {
                throw new ServiceException(String.format(
                        "Role already exists with [alias: %s]", entity.getRoleAlias()));
            }

            return super.add(entity);
        }

        throw new ServiceException(String.format("Invalid model data to add, %s", entity));
    }

    @Override
    public SystemRoleModel update(SystemRoleModel entity)
    {
        if (entity.isValidInput() && EntityUtils.isValidEntityId(entity.getId()))
        {
            // check if entity exists in db
            Asserts.assertEntityNotNullById(repository, entity.getId());

            // check if role alias exists
            SystemRoleModel entityInDb = roleRepository.getByAlias(entity.getRoleAlias());
            if (entityInDb != null && !entityInDb.getId().equals(entity.getId()))
            {
                throw new ServiceException(String.format(
                        "Role already exists with [alias: %s]", entity.getRoleAlias()));
            }

            return super.add(entity);
        }

        throw new ServiceException(String.format("Invalid model data to add, %s", entity));
    }

    public SystemRoleModel findByAlias (String alias)
    {
        return roleRepository.getByAlias(alias);
    }
}
