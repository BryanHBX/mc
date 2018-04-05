package org.edu.timelycourse.mc.biz.service.system;

import org.edu.timelycourse.mc.biz.entity.system.SystemConfig;
import org.edu.timelycourse.mc.biz.repository.system.SystemConfigRepository;
import org.edu.timelycourse.mc.biz.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by x36zhao on 2017/3/17.
 */
@Service
public class SystemConfigService extends BaseService<SystemConfig>
{
    private static Logger LOGGER = LoggerFactory.getLogger(SystemConfigService.class);

    private SystemConfigRepository repository;

    @Autowired
    public SystemConfigService(SystemConfigRepository repository)
    {
        super(repository);
        this.repository = repository;
    }

    public SystemConfig getByPropertyName(String propertyName)
    {
        return repository.getByPropertyName(propertyName);
    }

    public Integer deleteByPropertyName(String propertyName)
    {
        return repository.deleteByPropertyName(propertyName);
    }
}
