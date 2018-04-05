package org.edu.timelycourse.mc.api.controller.system;

import com.google.common.collect.Lists;
import org.apache.logging.log4j.util.Strings;
import org.edu.timelycourse.mc.api.controller.BaseController;
import org.edu.timelycourse.mc.biz.entity.system.SystemConfig;
import org.edu.timelycourse.mc.biz.service.system.SystemConfigService;
import org.edu.timelycourse.mc.common.entity.ResponseData;
import org.edu.timelycourse.mc.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by x36zhao on 2018/4/3.
 */
@RestController
@RequestMapping("/api/${api.version}/system/config")
public class SystemConfigController extends BaseController
{
    private static Logger LOGGER = LoggerFactory.getLogger(SystemConfigController.class);

    @Autowired
    private SystemConfigService sysConfigService;

    @RequestMapping(path="", method= RequestMethod.GET)
    public ResponseData<List<SystemConfig>> getConfigs(@RequestParam(required = false) String propertyName)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter getConfigs - [propertyName: %s]", propertyName));

        if (Strings.isNotEmpty(propertyName))
        {
            SystemConfig entity = sysConfigService.getByPropertyName(propertyName);
            return new ResponseData<List<SystemConfig>>(entity != null, Lists.newArrayList(entity),
                    String.format("Config name (%s) does not exist", propertyName));
        }

        return new ResponseData<List<SystemConfig>>(sysConfigService.getAll());
    }

    @RequestMapping(path="/{configId}", method= RequestMethod.DELETE)
    public ResponseData<Integer> deleteConfigByName (@PathVariable Integer configId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter deleteConfigByName - [configId: %d]", configId));

        SystemConfig entity = this.sysConfigService.get(configId);
        if (entity != null)
        {
            Integer result = this.sysConfigService.delete(entity.getId());
            return new ResponseData<Integer>(result != null);
        }
        else
        {
            return new ResponseData<Integer>(false, null,
                    String.format("Config does not exist - [id: %d]", configId));
        }
    }

    @RequestMapping(path="", method= RequestMethod.POST)
    public ResponseData<SystemConfig> addConfig (@RequestBody SystemConfig systemConfig)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter addConfig - [systemConfig: %s]", systemConfig));

        try
        {
            SystemConfig sysConfig = sysConfigService.getByPropertyName(systemConfig.getPropertyName());
            if (sysConfig == null)
            {
                return new ResponseData<SystemConfig>(sysConfigService.add(systemConfig));
            }
            else
            {
                return new ResponseData<SystemConfig>(false, null,
                        String.format("Config name (%s) already exists", sysConfig.getPropertyName()));
            }
        }
        catch (ServiceException ex)
        {
            return new ResponseData<SystemConfig>(false, null, ex.getMessage());
        }
    }

    @RequestMapping(path="/{configId}", method= RequestMethod.PATCH)
    public ResponseData<SystemConfig> updateConfig (@PathVariable Integer configId,
                                                  @RequestBody SystemConfig systemConfig)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter updateConfig - [configId: %d, systemConfig: %s]", configId, systemConfig));

        SystemConfig entity = this.sysConfigService.get(configId);
        if (entity != null)
        {
            return new ResponseData<SystemConfig>(this.sysConfigService.update(systemConfig));
        }
        else
        {
            return new ResponseData<SystemConfig>(false, null,
                    String.format("Config does not exist - [id: %d]", configId));
        }
    }
}
