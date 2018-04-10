package org.edu.timelycourse.mc.web.controller;

import com.google.common.reflect.TypeToken;
import org.edu.timelycourse.mc.biz.model.SystemConfigModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
    public String showSystemSettings (
            Model model,
            @RequestParam(required = false, value = "id") Integer configId)
    {
        if (configId != null && configId > 0)
        {
            model.addAttribute("config", fetchConfigById(configId));
            return getModulePage("system/pages/configListPage");
        }

        model.addAttribute("configs", remoteCall("system/config",
                new TypeToken<List<SystemConfigModel>>() {}).getData());

        return getModulePage("system/config");
    }

    @RequestMapping("/system/settings/dialog")
    public String showSystemSettingDialog (
            Model model,
            @RequestParam(required = false, value = "id")  Integer configId,
            @RequestParam(required = false, value = "pid") Integer parentId)
    {
        if (configId != null && configId > 0)
        {
            model.addAttribute("config", fetchConfigById(configId));
        }

        if (parentId != null && parentId > 0)
        {
            model.addAttribute("parentId", parentId);
            model.addAttribute("parent", fetchConfigById(parentId));
        }

        return getModulePage("system/dialog/dialogConfigField");
    }

    private SystemConfigModel fetchConfigById (Integer configId)
    {
        return remoteCall("system/config/" + configId, new TypeToken<SystemConfigModel>() {}).getData();
    }

    protected String getMyModulePath()
    {
        return "admin";
    }
}
