package org.edu.energycourse.mc.api.user;

import org.edu.energycourse.mc.biz.entity.User;
import org.edu.energycourse.mc.biz.service.UserService;
import org.edu.energycourse.mc.common.entity.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by x36zhao on 2018/4/3.
 */
@RestController
@RequestMapping("/api/${version}/users")
public class UserApiController
{
    private static Logger LOGGER = LoggerFactory.getLogger(UserApiController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(path="", method= RequestMethod.GET)
    public ResponseData<List<User>> getAllUsers()
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug(String.format("Enter getAllUsers"));
        }

        return new ResponseData<List<User>>(userService.getAllUsers());
    }
}
