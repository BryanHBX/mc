package org.edu.energycourse.mc.api.controller.member;

import org.edu.energycourse.mc.api.controller.BaseController;
import org.edu.energycourse.mc.biz.entity.member.User;
import org.edu.energycourse.mc.biz.service.member.UserService;
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
@RequestMapping("/api/${api.version}/member")
public class UserController extends BaseController
{
    private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(path="", method= RequestMethod.GET)
    public ResponseData<List<User>> getAllUsers()
    {
        if (LOGGER.isDebugEnabled())
        {
        }

        return new ResponseData<List<User>>(userService.getAll());
    }
}
