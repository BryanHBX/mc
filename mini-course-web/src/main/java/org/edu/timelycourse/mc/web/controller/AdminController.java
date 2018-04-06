package org.edu.timelycourse.mc.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController extends AbstractController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping("/user/org")
    public String showOrganizationUserList ()
    {
        return getModulePage("user/userOrganizationList");
    }

    @RequestMapping("/user/individual")
    public String showIndividualUserList ()
    {
        return getModulePage("user/userIndividualList");
    }

    @RequestMapping("/system/settings")
    public String showSystemSettings ()
    {
        return getModulePage("system/systemSettings");
    }

    @RequestMapping("/system/settings/dialog")
    public String showSystemSettingDialog ()
    {
        return getModulePage("system/dialog/dialogSystemField");
    }

    protected String getMyModulePath()
    {
        return "admin";
    }
}
