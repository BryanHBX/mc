package org.edu.timelycourse.mc.biz.service.system;

import org.edu.timelycourse.mc.biz.entity.system.SystemConfigValue;
import org.edu.timelycourse.mc.biz.repository.system.SystemConfigValueRepository;
import org.edu.timelycourse.mc.biz.service.BaseService;
import org.edu.timelycourse.mc.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemConfigValueService extends BaseService<SystemConfigValue>
{
    private static Logger LOGGER = LoggerFactory.getLogger(SystemConfigValueService.class);

    private SystemConfigValueRepository repository;

    public SystemConfigValueService(SystemConfigValueRepository repository)
    {
        super(repository);
        this.repository = repository;
    }

    @Override
    public SystemConfigValue add (SystemConfigValue configValue)
    {
        SystemConfigValue entity = repository.getByConfigValue(configValue.getConfigValue());
        if (entity == null)
        {
            return super.add(entity);
        }

        throw new ServiceException(String.format(
                "The config value (%s) against config (id: %d) already exists.",
                entity.getConfigValue(), entity.getConfigId()));
    }

    public List<SystemConfigValue> getByConfigId (Integer configId)
    {
        try
        {
            return this.repository.getByConfigId(configId);
        }
        catch (Exception ex)
        {
            throw new ServiceException(String.format("Failed to get config values by id: %d", configId), ex);
        }
    }
}
