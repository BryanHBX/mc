package org.edu.timelycourse.mc.biz.service;

import org.edu.timelycourse.mc.biz.model.SystemConfigModel;
import org.edu.timelycourse.mc.biz.repository.SystemConfigRepository;
import org.edu.timelycourse.mc.biz.repository.SystemConfigValueRepository;
import org.edu.timelycourse.mc.biz.utils.Asserts;
import org.edu.timelycourse.mc.common.exception.ServiceException;
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

    private SystemConfigRepository sysConfigRepository;
    private SystemConfigValueRepository sysConfigValueRepository;

    @Autowired
    public SystemConfigService(SystemConfigRepository sysConfigRepository,
                               SystemConfigValueRepository sysConfigValueRepository)
    {
        super(sysConfigRepository);

        this.sysConfigRepository = sysConfigRepository;
        this.sysConfigValueRepository = sysConfigValueRepository;
    }

    @Override
    public SystemConfigModel add (SystemConfigModel entity)
    {
        SystemConfigModel config = sysConfigRepository.getByConfigName(entity.getConfigName());
        if (config == null)
        {
            if (entity.getParentId() != null && entity.getParentId() > 0)
            {
                Asserts.assertEntityNotNullById(sysConfigRepository, entity.getParentId());
            }

            return super.add(entity);
        }

        throw new ServiceException(String.format(
                "Config already exists with name: %s", config.getConfigName()));
    }

    @Override
    public SystemConfigModel update (SystemConfigModel entity)
    {
        SystemConfigModel config = sysConfigRepository.getByConfigName(entity.getConfigName());
        if (config == null || config.getId().equals(entity.getId()))
        {
            return super.update(entity);
        }

        throw new ServiceException(String.format(
                "Config already exists with name: %s", config.getConfigName()));
    }

    @Override
    public Integer delete(Integer configId)
    {
        Asserts.assertEntityNotNullById(sysConfigRepository, configId);
        sysConfigValueRepository.deleteByConfigId(configId);
        return sysConfigRepository.delete(configId);
    }

    public SystemConfigModel getByConfigName(String configName)
    {
        return assertEntityNotNullByName(configName);
    }

    public List<SystemConfigModel> getChildrenConfig (Integer parentId)
    {
        Asserts.assertEntityNotNullById(sysConfigRepository, parentId);
        return sysConfigRepository.getChildrenConfig(parentId);
    }

    public Integer deleteByConifgName(String configName)
    {
        SystemConfigModel config = assertEntityNotNullByName(configName);
        return sysConfigValueRepository.deleteByConfigId(config.getId()) + sysConfigRepository.delete(config.getId());
    }

    private SystemConfigModel assertEntityNotNullByName (String configName)
    {
        SystemConfigModel entity = sysConfigRepository.getByConfigName(configName);
        if (entity != null)
        {
            return entity;
        }

        throw new ServiceException(String.format("Entity does not exist with specified name: %s", configName));
    }
}
