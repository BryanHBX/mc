package org.edu.timelycourse.mc.biz.service;

import org.edu.timelycourse.mc.biz.model.SystemConfigModel;
import org.edu.timelycourse.mc.biz.repository.SystemConfigRepository;
import org.edu.timelycourse.mc.biz.utils.Asserts;
import org.edu.timelycourse.mc.common.exception.ServiceException;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by x36zhao on 2017/3/17.
 */
@Service
public class SystemConfigService extends BaseService<SystemConfigModel>
{
    private static Logger LOGGER = LoggerFactory.getLogger(SystemConfigService.class);

    private SystemConfigRepository configRepository;

    @Autowired
    public SystemConfigService(SystemConfigRepository repository)
    {
        super(repository);
        this.configRepository = repository;
    }

    @Override
    public SystemConfigModel add (SystemConfigModel entity)
    {
        if (entity.isValidInput())
        {
            // check if parent entity exists if given
            if (entity.getParentId() != null && EntityUtils.isValidEntityId(entity.getParentId()))
            {
                Asserts.assertEntityNotNullById(repository, entity.getParentId());
            }

            // check if same entity
            if (configRepository.getByEntity(new SystemConfigModel(
                    entity.getConfigName(), null, entity.getParentId())) != null)
            {
                throw new ServiceException(String.format(
                        "Config already exists with [name: %s, parent: %d]",
                        entity.getConfigName(), entity.getParentId()));
            }

            return super.add(entity);
        }

        throw new ServiceException(String.format(
                "Invalid model input to add, %s", entity));
    }

    @Override
    public SystemConfigModel update (SystemConfigModel entity)
    {
        if (entity.isValidInput() && EntityUtils.isValidEntityId(entity.getId()))
        {
            // check if entity exists with given id
            SystemConfigModel entityInDb = (SystemConfigModel) Asserts.assertEntityNotNullById(repository, entity.getId());

            // check if any change with respect to its parent
            if (entityInDb.getParentId() != entity.getParentId())
            {
                throw new ServiceException(String.format(
                        "It's not allowed to change the parent from %d to %d",
                        entityInDb.getParentId(), entity.getParentId()));
            }

            // check if config name already exists
            entityInDb = repository.getByEntity(new SystemConfigModel(
                    entity.getConfigName(), null, entity.getParentId()
            ));

            if (entityInDb != null && !entityInDb.getId().equals(entity.getId()))
            {
                throw new ServiceException(String.format(
                        "Config already exists with [name: %s, parent: %d]", entity.getConfigName(), entity.getParentId()));
            }

            return super.update(entity);
        }

        throw new ServiceException(String.format("Invalid model input to update, %s", entity));
    }

    @Override
    public Integer delete(Integer id)
    {
        if (EntityUtils.isValidEntityId(id))
        {
            SystemConfigModel entity = (SystemConfigModel) Asserts.assertEntityNotNullById(repository, id);
            if (entity.getChildren() != null)
            {
                for (SystemConfigModel child : entity.getChildren())
                {
                    delete(child.getId());
                }
            }

            return repository.delete(id);
        }

        throw new ServiceException(String.format("Invalid entity id (%d) for deletion", id));
    }

    public SystemConfigModel getByConfigName(String configName)
    {
        return assertEntityNotNullByName(configName);
    }

    public List<SystemConfigModel> getChildrenConfig (Integer parentId)
    {
        Asserts.assertEntityNotNullById(repository, parentId);
        return configRepository.getChildrenConfig(parentId);
    }

    public Integer deleteByConifgName(String configName)
    {
        SystemConfigModel config = assertEntityNotNullByName(configName);
        return configRepository.delete(config.getId());
    }

    private SystemConfigModel assertEntityNotNullByName (String configName)
    {
        SystemConfigModel entity = configRepository.getByConfigName(configName);
        if (entity != null)
        {
            return entity;
        }

        throw new ServiceException(String.format("Entity does not exist with specified name: %s", configName));
    }
}
