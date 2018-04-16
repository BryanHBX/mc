package org.edu.timelycourse.mc.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.edu.timelycourse.mc.biz.enums.EAuthorityName;
import org.edu.timelycourse.mc.biz.enums.EUserRole;
import org.edu.timelycourse.mc.biz.model.SystemRoleModel;
import org.edu.timelycourse.mc.biz.service.SystemRoleService;
import org.edu.timelycourse.mc.biz.utils.Asserts;
import org.edu.timelycourse.mc.common.entity.ResponseData;
import org.edu.timelycourse.mc.common.exception.ServiceException;
import org.edu.timelycourse.mc.common.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by x36zhao on 2018/4/3.
 */
@RestController
@RequestMapping("/api/${api.version}/system/role")
@Api(tags = { "平台角色API" })
public class SystemRoleController extends BaseController
{
    private static Logger LOGGER = LoggerFactory.getLogger(SystemRoleController.class);

    @Autowired
    private SystemRoleService roleService;

    @RequestMapping(path="", method= RequestMethod.GET)
    @ApiOperation(value = "Get all roles")
    public ResponseData getRoles(@RequestParam(value = "alias", required = false) String roleAlias,
                                 @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Enter getRoles - [roleAlias: {}]", roleAlias);

        try
        {
            if (StringUtil.isNotEmpty(roleAlias))
            {
                return ResponseData.success(roleService.findByAlias(roleAlias));
            }

            return ResponseData.success(roleService.getAll());
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="/{roleId}", method= RequestMethod.GET)
    @ApiOperation(value = "Get role by given id")
    public ResponseData getRoleById(@PathVariable(required = true) Integer roleId,
                                    @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Enter getRoleById - [roleId: {}]", roleId);

        try
        {
            return ResponseData.success(Asserts.assertEntityNotNullById(roleService, roleId));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="/{roleId}", method= RequestMethod.DELETE)
    @ApiOperation(value = "Delete role by given id")
    @PreAuthorize("hasRole('ROLE_SUPERUSER')")
    public ResponseData deleteRoleById (@PathVariable(required = true) Integer roleId,
                                        @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Enter deleteRoleById - [roleId: {}]", roleId);

        try
        {
            Asserts.assertEntityNotNullById(roleService, roleId);
            return ResponseData.success(roleService.delete(roleId));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="", method= RequestMethod.POST)
    @ApiOperation(value = "Add role by given entity")
    @PreAuthorize("hasRole('ROLE_SUPERUSER')")
    public ResponseData addRole (@RequestBody SystemRoleModel model,
                                 @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Enter addRole - [model: {}]", model);

        try
        {
            return ResponseData.success(roleService.add(model));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="/{roleId}", method= RequestMethod.PATCH)
    @ApiOperation(value = "Update role with respect to the specified id")
    public ResponseData updateRole (@PathVariable(required = true) Integer roleId,
                                    @RequestBody SystemRoleModel model,
                                    @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Enter updateRole - [roleId: {}, model: {}]", roleId, model);

        try
        {
            Asserts.assertEntityNotNullById(roleService, roleId);

            // in order to avoid overwritten id in request body
            model.setId(roleId);
            return ResponseData.success(this.roleService.update(model));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }
}