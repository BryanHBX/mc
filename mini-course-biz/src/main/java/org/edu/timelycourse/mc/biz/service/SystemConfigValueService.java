package org.edu.timelycourse.mc.biz.service;

import org.edu.timelycourse.mc.biz.model.SystemConfigValue;
import org.edu.timelycourse.mc.biz.repository.SystemConfigRepository;
import org.edu.timelycourse.mc.biz.repository.SystemConfigValueRepository;
import org.edu.timelycourse.mc.biz.utils.Asserts;
import org.edu.timelycourse.mc.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemConfigValueService extends BaseService<SystemConfigValue>
{
    private static Logger LOGGER = LoggerFactory.getLogger(SystemConfigValueService.class);

    private SystemConfigRepository configRepository;
    private SystemConfigValueRepository configValueRepository;

    @Autowired
    public SystemConfigValueService(
            SystemConfigValueRepository configValueRepository,
            SystemConfigRepository configRepository)
    {
        super(configValueRepository);
        this.configValueRepository = configValueRepository;
        this.configRepository = configRepository;
    }

    @Override
    public SystemConfigValue add (SystemConfigValue entity)
    {
        Asserts.assertEntityNotNullById(configRepository, entity.getConfigId());
        SystemConfigValue configValue = configValueRepository.getByConfigValue(entity);
        if (configValue == null)
        {
            return super.add(entity);
        }

        throw new ServiceException(String.format(
                "The config value (%s) against config (id: %d) already exists.",
                entity.getConfigValue(), entity.getConfigId()));
    }

    @Override
    public SystemConfigValue update(SystemConfigValue entity)
    {
        Asserts.assertEntityNotNullById(configRepository, entity.getConfigId());
        SystemConfigValue configValue = configValueRepository.getByConfigValue(entity);
        if (configValue == null || configValue.getId().equals(entity.getId()))
        {
            return super.update(entity);
        }

        throw new ServiceException(messageSource.getMessage("sys.config.value.exists", entity.getConfigValue()));
                //String.format(
                //"The config value (%s) against config (id: %d) already exists.",
                //messageSource.getMessage("sys.config.value.exists"), entity.getConfigValue()));
                //entity.getConfigValue(), entity.getConfigId()));
    }

    public List<SystemConfigValue> getByConfigId (Integer configId)
    {
        try
        {
            return this.configValueRepository.getByConfigId(configId);
        }
        catch (Exception ex)
        {
            throw new ServiceException(String.format(
                    "Failed to get config values by id: %d", configId), ex);
        }
    }
}
