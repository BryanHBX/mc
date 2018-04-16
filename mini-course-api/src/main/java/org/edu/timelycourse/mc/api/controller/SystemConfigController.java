package org.edu.timelycourse.mc.api.controller;

import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.edu.timelycourse.mc.biz.model.SystemConfigModel;
import org.edu.timelycourse.mc.biz.service.SystemConfigService;
import org.edu.timelycourse.mc.biz.utils.Asserts;
import org.edu.timelycourse.mc.common.entity.ResponseData;
import org.edu.timelycourse.mc.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by x36zhao on 2018/4/3.
 */
@RestController
@RequestMapping("/api/${api.version}/system/config")
@Api(tags = { "平台配置API" })
public class SystemConfigController extends BaseController
{
    private static Logger LOGGER = LoggerFactory.getLogger(SystemConfigController.class);

    @Autowired
    private SystemConfigService sysConfigService;

    @RequestMapping(path="", method= RequestMethod.GET)
    @ApiOperation(value = "Get either list of all configs or by given config name")
    public ResponseData getConfigs(@RequestParam(required = false) String configName,
                                   @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter getConfigs - [configName: %s]", configName));

        try
        {
            if (Strings.isNotEmpty(configName))
            {
                return ResponseData.success(assertEntityNotNullByName(configName));
            }

            return ResponseData.success(sysConfigService.getAll());
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="/{configId}", method= RequestMethod.GET)
    @ApiOperation(value = "Get config by given id")
    public ResponseData getConfigById(@PathVariable(required = true) Integer configId,
                                      @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter getConfigById - [configId: %d]", configId));

        try
        {
            return ResponseData.success(Asserts.assertEntityNotNullById(sysConfigService, configId));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="/{configId}", method= RequestMethod.DELETE)
    @ApiOperation(value = "Delete config by given config id")
    @PreAuthorize("hasRole('ROLE_SUPERUSER')")
    public ResponseData deleteConfigById (@PathVariable(required = true) Integer configId,
                                          @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter deleteConfigByName - [configId: %d]", configId));

        try
        {
            Asserts.assertEntityNotNullById(sysConfigService, configId);
            return ResponseData.success(sysConfigService.delete(configId));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="", method= RequestMethod.POST)
    @ApiOperation(value = "Add config by given config entity")
    @PreAuthorize("hasRole('ROLE_SUPERUSER')")
    public ResponseData addConfig (@RequestBody SystemConfigModel systemConfig,
                                   @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter addConfig - [systemConfig: %s]", systemConfig));

        try
        {
            return ResponseData.success(sysConfigService.add(systemConfig));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="/{configId}", method= RequestMethod.PATCH)
    @ApiOperation(value = "Update config with respect to the specified id")
    @PreAuthorize("hasRole('ROLE_SUPERUSER')")
    public ResponseData updateConfig (@PathVariable(required = true) Integer configId,
                                      @RequestBody SystemConfigModel systemConfig,
                                      @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter updateConfig - [configId: %d, systemConfig: %s]", configId, systemConfig));

        try
        {
            Asserts.assertEntityNotNullById(sysConfigService, configId);
            // in order to avoid overwritten id in request body
            systemConfig.setId(configId);

            return ResponseData.success(this.sysConfigService.update(systemConfig));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    private SystemConfigModel assertEntityNotNullByName (String configName)
    {
        SystemConfigModel entity = sysConfigService.getByConfigName(configName);
        if (entity != null)
        {
            return entity;
        }

        throw new ServiceException(String.format("Entity does not exist with specified name: %s", configName));
    }
}