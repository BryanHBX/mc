package org.edu.timelycourse.mc.api.controller.system;

import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.edu.timelycourse.mc.api.controller.BaseController;
import org.edu.timelycourse.mc.biz.entity.system.SystemConfig;
import org.edu.timelycourse.mc.biz.entity.system.SystemConfigValue;
import org.edu.timelycourse.mc.biz.service.system.SystemConfigService;
import org.edu.timelycourse.mc.biz.service.system.SystemConfigValueService;
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
@Api(value = "Test", tags = { "平台配置API" })
public class SystemConfigController extends BaseController
{
    private static Logger LOGGER = LoggerFactory.getLogger(SystemConfigController.class);

    @Autowired
    private SystemConfigService sysConfigService;

    @Autowired
    private SystemConfigValueService sysConfigValueService;

    @RequestMapping(path="", method= RequestMethod.GET)
    @ApiOperation(value = "Get either list of all configs or by given config name")
    public ResponseData<List<SystemConfig>> getConfigs(
            @RequestParam(required = false) String configName)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter getConfigs - [configName: %s]", configName));

        try
        {
            if (Strings.isNotEmpty(configName))
            {
                SystemConfig entity = sysConfigService.getByConfigName(configName);
                return new ResponseData<List<SystemConfig>>(true, Lists.newArrayList(entity), null);
            }

            return new ResponseData<List<SystemConfig>>(sysConfigService.getAll());
        }
        catch (ServiceException ex)
        {
            return new ResponseData<List<SystemConfig>>(false, null, ex.getMessage());
        }
    }

    @RequestMapping(path="/{configId}", method= RequestMethod.DELETE)
    @ApiOperation(value = "Delete config by given config id")
    public ResponseData<Integer> deleteConfigById (
            @PathVariable Integer configId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter deleteConfigByName - [configId: %d]", configId));

        try
        {
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
        catch (ServiceException ex)
        {
            return new ResponseData<Integer>(false, null, ex.getMessage());
        }
    }

    @RequestMapping(path="", method= RequestMethod.POST)
    @ApiOperation(value = "Add config by given config entity")
    public ResponseData<SystemConfig> addConfig (
            @RequestBody SystemConfig systemConfig)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter addConfig - [systemConfig: %s]", systemConfig));

        try
        {
            return new ResponseData<SystemConfig>(sysConfigService.add(systemConfig));
        }
        catch (ServiceException ex)
        {
            return new ResponseData<SystemConfig>(false, null, ex.getMessage());
        }
    }

    @RequestMapping(path="/{configId}", method= RequestMethod.PATCH)
    @ApiOperation(value = "Update config with respect to the specified id")
    public ResponseData<SystemConfig> updateConfig (
            @PathVariable Integer configId,
            @RequestBody SystemConfig systemConfig)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter updateConfig - [configId: %d, systemConfig: %s]", configId, systemConfig));

        try
        {
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
        catch (ServiceException ex)
        {
            return new ResponseData<SystemConfig>(false, null, ex.getMessage());
        }

    }

    // ==================== System Config Value API ==============================/

    @RequestMapping(path="/{configId}/values", method= RequestMethod.GET)
    @ApiOperation(value = "Get config values by given config id")
    public ResponseData<List<SystemConfigValue>> getConfigValues(
            @PathVariable(required = true) Integer configId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter getConfigValues - [configId: %d]", configId));

        try
        {
            SystemConfig entity = sysConfigService.get(configId);
            if (entity != null)
            {
                return new ResponseData<List<SystemConfigValue>>(sysConfigValueService.getByConfigId(configId));
            }
            else
            {
                return new ResponseData<List<SystemConfigValue>>(false, null,
                        String.format("Config does not exist - [id: %d]", configId));
            }
        }
        catch (ServiceException ex)
        {
            return new ResponseData<List<SystemConfigValue>>(false, null, ex.getMessage());
        }
    }

    @RequestMapping(path="/{configId}/values/{id}", method= RequestMethod.DELETE)
    @ApiOperation(value = "Delete config values by given config id and value id")
    public ResponseData<Integer> deleteConfigValue(
            @PathVariable(required = true) Integer configId,
            @PathVariable(required = true) Integer id)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter deleteConfigValue - [configId: %d, id: %d]", configId, id));

        try
        {
            SystemConfig entity = sysConfigService.get(configId);
            if (entity != null)
            {
                return new ResponseData<Integer>(sysConfigValueService.delete(id) != null);
            }
            else
            {
                return new ResponseData<Integer>(false, null,
                        String.format("Config does not exist - [id: %d]", configId));
            }
        }
        catch (ServiceException ex)
        {
            return new ResponseData<Integer>(false, null, ex.getMessage());
        }
    }

    @RequestMapping(path="/{configId}/values", method= RequestMethod.POST)
    @ApiOperation(value = "Add config value against the specified config")
    public ResponseData<SystemConfigValue> addConfigValue (
            @PathVariable(required = true) Integer configId,
            @RequestBody SystemConfigValue configValue)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter addConfigValue - [configId: %d, value: %s]", configId, configValue));

        try
        {
            SystemConfig entity = sysConfigService.get(configId);
            if (entity != null)
            {
                configValue.setConfigId(configId);
                return new ResponseData<SystemConfigValue>(sysConfigValueService.add(configValue));
            }
            else
            {
                return new ResponseData<SystemConfigValue>(false, null,
                        String.format("Config does not exist - [id: %d]", configId));
            }
        }
        catch (ServiceException ex)
        {
            return new ResponseData<SystemConfigValue>(false, null, ex.getMessage());
        }
    }

    @RequestMapping(path="/{configId}/values/{id}", method= RequestMethod.PATCH)
    @ApiOperation(value = "Update config value with respect to the specified id")
    public ResponseData<SystemConfigValue> updateConfigValue (
            @PathVariable(required = true) Integer configId,
            @PathVariable(required = true) Integer id,
            @RequestBody SystemConfigValue configValue)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter updateConfigValue - [configId: %d, id: %d, value: %s]", configId, id, configValue));

        try
        {
            SystemConfig config = sysConfigService.get(configId);
            if (config != null)
            {
                SystemConfigValue entity = sysConfigValueService.get(id);
                if (entity != null)
                {
                    configValue.setConfigId(configId);
                    configValue.setId(id);
                    return new ResponseData<SystemConfigValue>(sysConfigValueService.update(configValue));
                }
                else
                {
                    return new ResponseData<SystemConfigValue>(false, null,
                            String.format("Config value does not exist - [id: %d]", id));
                }
            }
            else
            {
                return new ResponseData<SystemConfigValue>(false, null,
                        String.format("Config does not exist - [id: %d]", configId));
            }
        }
        catch (ServiceException ex)
        {
            return new ResponseData<SystemConfigValue>(false, null, ex.getMessage());
        }

    }
}
