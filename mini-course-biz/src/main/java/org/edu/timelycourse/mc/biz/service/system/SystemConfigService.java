package org.edu.timelycourse.mc.biz.service.system;

import org.edu.timelycourse.mc.biz.model.system.SystemConfig;
import org.edu.timelycourse.mc.biz.repository.system.SystemConfigRepository;
import org.edu.timelycourse.mc.biz.repository.system.SystemConfigValueRepository;
import org.edu.timelycourse.mc.biz.service.BaseService;
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
public class SystemConfigService extends BaseService<SystemConfig>
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
    public SystemConfig add (SystemConfig entity)
    {
        SystemConfig config = sysConfigRepository.getByConfigName(entity.getConfigName());
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
    public SystemConfig update (SystemConfig entity)
    {
        SystemConfig config = sysConfigRepository.getByConfigName(entity.getConfigName());
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

    public SystemConfig getByConfigName(String configName)
    {
        return assertEntityNotNullByName(configName);
    }

    public List<SystemConfig> getChildrenConfig (Integer parentId)
    {
        Asserts.assertEntityNotNullById(sysConfigRepository, parentId);
        return sysConfigRepository.getChildrenConfig(parentId);
    }

    public Integer deleteByConifgName(String configName)
    {
        SystemConfig config = assertEntityNotNullByName(configName);
        return sysConfigValueRepository.deleteByConfigId(config.getId()) + sysConfigRepository.delete(config.getId());
    }

    private SystemConfig assertEntityNotNullByName (String configName)
    {
        SystemConfig entity = sysConfigRepository.getByConfigName(configName);
        if (entity != null)
        {
            return entity;
        }

        throw new ServiceException(String.format("Entity does not exist with specified name: %s", configName));
    }
}
