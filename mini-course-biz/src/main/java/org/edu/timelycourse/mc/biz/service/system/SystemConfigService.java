package org.edu.timelycourse.mc.biz.service.system;

import org.edu.timelycourse.mc.biz.entity.system.SystemConfig;
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
            return super.add(entity);
        }

        throw new ServiceException(String.format(
                "Config already exists with name: %s", config.getConfigName()));
    }

    @Override
    public SystemConfig update (SystemConfig entity)
    {
        SystemConfig config = getByConfigName(entity.getConfigName());
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
        SystemConfig config = sysConfigRepository.getByConfigName(configName);
        if (config != null)
        {
            //config.setValues(sysConfigValueRepository.getByConfigId(config.getId()));
            return config;
        }

        throw new ServiceException(String.format("Config name (%s) does not exist", configName));
    }

    public Integer deleteByConifgName(String configName)
    {
        SystemConfig config = sysConfigRepository.getByConfigName(configName);
        if (config != null)
        {
            sysConfigValueRepository.deleteByConfigId(config.getId());
            return sysConfigRepository.delete(config.getId());
        }

        throw new ServiceException(String.format("Config name (%s) does not exist", configName));
    }
}
