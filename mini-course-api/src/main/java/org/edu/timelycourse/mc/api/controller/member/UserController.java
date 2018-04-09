package org.edu.timelycourse.mc.api.controller.member;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.edu.timelycourse.mc.api.controller.BaseController;
import org.edu.timelycourse.mc.biz.model.member.User;
import org.edu.timelycourse.mc.biz.service.member.UserService;
import org.edu.timelycourse.mc.biz.utils.Asserts;
import org.edu.timelycourse.mc.common.entity.ResponseData;
import org.edu.timelycourse.mc.common.exception.ServiceException;
import org.edu.timelycourse.mc.common.utils.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by x36zhao on 2018/4/3.
 */
@RestController
@RequestMapping("/api/${api.version}/member")
@Api(tags = { "平台用户API" })
public class UserController extends BaseController
{
    private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(path="", method= RequestMethod.GET)
    @ApiOperation(value = "Get either list of all users or by given query which is either phone or identity")
    public ResponseData getUser(@RequestParam(required = false, value = "query") String query)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Enter getUser - [phone: %s, query: %s]", query);

        try
        {
            if (!Strings.isNotEmpty(query))
            {
                return ValidatorUtil.isMobile(query) ?
                        ResponseData.success(userService.findByUserPhone(query)) :
                        ResponseData.success(userService.findByUserIdentity(query));
            }

            return ResponseData.success(userService.getAll());
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="/{userId}", method= RequestMethod.GET)
    @ApiOperation(value = "Get user by given id")
    public ResponseData getUserById(@PathVariable(required = true) Integer userId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter getUserById - [userId: %d]", userId));

        try
        {
            return ResponseData.success(Asserts.assertEntityNotNullById(userService, userId));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="", method= RequestMethod.POST)
    @ApiOperation(value = "Add user by given entity")
    public ResponseData addUser (@RequestBody User user)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter addUser - [user: %s]", user));

        try
        {
            return ResponseData.success(userService.add(user));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="/{userId}", method= RequestMethod.DELETE)
    @ApiOperation(value = "Delete user by given id")
    public ResponseData deleteUserById (
            @PathVariable(required = true) Integer userId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter deleteUserById - [userId: %d]", userId));

        try
        {
            Asserts.assertEntityNotNullById(userService, userId);
            return ResponseData.success(userService.delete(userId));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="/{userId}", method= RequestMethod.PATCH)
    @ApiOperation(value = "Update user with respect to the specified id")
    public ResponseData updateUser (
            @PathVariable(required = true) Integer userId,
            @RequestBody User user)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter updateUser - [userId: %d, user: %s]", userId, user));

        try
        {
            Asserts.assertEntityNotNullById(userService, userId);

            // in order to avoid overwritten id in the payload
            user.setId(userId);

            return ResponseData.success(this.userService.update(user));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }
}
