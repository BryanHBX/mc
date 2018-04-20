package org.edu.timelycourse.mc.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.edu.timelycourse.mc.beans.model.UserModel;
import org.edu.timelycourse.mc.biz.service.UserService;
import org.edu.timelycourse.mc.biz.utils.Asserts;
import org.edu.timelycourse.mc.biz.utils.SecurityContextHelper;
import org.edu.timelycourse.mc.beans.entity.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @ModelAttribute("member")
    public UserModel getModel()
    {
        return new UserModel ();
    }

    @RequestMapping(path="", method= RequestMethod.GET)
    @ApiOperation(value = "Get either list of all users or by given query")
    public ResponseData getUser(@RequestParam(name="pageNum", required = false) Integer pageNum,
                                @RequestParam(name="pageSize", required = false) Integer pageSize,
                                @ModelAttribute("member") UserModel model,
                                @RequestHeader(name = "Authorization") String auth)
    {
        // set using the school id in the claim from JWT token
        model.setSchoolId(SecurityContextHelper.getPrincipal().getSid());

        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Enter getUser - [pageNum: {}, pageSize: {}, schoolInfo: {}]", pageNum, pageSize, model);
        }

        return ResponseData.success(userService.findByPage(model, pageNum, pageSize));
    }

    @RequestMapping(path="/{userId}", method= RequestMethod.GET)
    @ApiOperation(value = "Get user by given id")
    public ResponseData getUserById(@PathVariable(required = true) Integer userId,
                                    @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug(String.format("Enter getUserById - [userId: %d]", userId));
        }

        UserModel entity = (UserModel) Asserts.assertEntityNotNullById(userService, userId);
        if (!entity.getSchoolId().equals(SecurityContextHelper.getPrincipal().getSid()))
        {
            throw new AccessDeniedException("No permission to get the user info");
        }

        return ResponseData.success(entity);
    }

    @RequestMapping(path="", method= RequestMethod.POST)
    @ApiOperation(value = "Add user by given entity")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR')")
    public ResponseData addUser (@RequestBody UserModel model,
                                 @RequestHeader(name = "Authorization") String auth)
    {
        // set using the school id in the claim from JWT token
        model.setSchoolId(SecurityContextHelper.getPrincipal().getSid());
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug(String.format("Enter addUser - [user: %s]", model));
        }
        return ResponseData.success(userService.add(model));
    }

    @RequestMapping(path="/{userId}", method= RequestMethod.DELETE)
    @ApiOperation(value = "Delete user by given id")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR')")
    public ResponseData deleteUserById (@PathVariable(required = true) Integer userId,
                                        @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug(String.format("Enter deleteUserById - [userId: %d]", userId));
        }
        Asserts.assertEntityNotNullById(userService, userId);
        return ResponseData.success(userService.delete(userId));
    }

    @RequestMapping(path="/{userId}", method= RequestMethod.PATCH)
    @ApiOperation(value = "Update user with respect to the specified id")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR')")
    public ResponseData updateUser (@PathVariable(required = true) Integer userId,
                                    @RequestBody UserModel user,
                                    @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug(String.format("Enter updateUser - [userId: %d, user: %s]", userId, user));
        }
        // in order to avoid overwritten id in the payload
        Asserts.assertEntityNotNullById(userService, userId);
        return ResponseData.success(this.userService.update(user));
    }


    @RequestMapping(path="/password/{userId}", method= RequestMethod.POST)
    @ApiOperation(value = "Reset user password")
    //@PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR')")
    public ResponseData resetUserPassword(@PathVariable(required = true) Integer userId,
                                          @RequestBody String password,
                                          @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Enter resetUserPassword - [userId: {}]", userId);
        }
        return ResponseData.success(userService.resetPassword(userId, password));
    }

    @RequestMapping(path="/stat/{userId}", method= RequestMethod.PATCH)
    @ApiOperation(value = "Reset user status")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR')")
    public ResponseData resetUserStatus(@PathVariable(required = true) Integer userId,
                                        @RequestParam(name="status", required = true) Integer userStatus,
                                        @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Enter resetUserPassword - [userId: {}, userStatus: {}]", userId, userStatus);
        }
        return ResponseData.success(userService.resetStatus(userId, userStatus));
    }
}
