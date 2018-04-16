package org.edu.timelycourse.mc.web.controller;

import com.google.common.reflect.TypeToken;
import org.edu.timelycourse.mc.biz.model.SystemConfigModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class SystemController extends AbstractController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemController.class);

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
    public String showSystemSettings (Model model,
                                      @RequestParam(required = false, value = "id") Integer configId,
                                      HttpServletRequest request)
    {
        if (configId != null && configId > 0)
        {
            model.addAttribute("config", fetchConfigById(request, configId));
            return getModulePage("system/pages/configListPage");
        }

        model.addAttribute("configs", fetchConfigs(request));

        return getModulePage("system/config");
    }

    @RequestMapping("/system/settings/dialog")
    public String showSystemSettingDialog (Model model,
                                           @RequestParam(required = false, value = "id")  Integer configId,
                                           @RequestParam(required = false, value = "pid") Integer parentId,
                                           HttpServletRequest request)
    {
        if (configId != null && configId > 0)
        {
            model.addAttribute("config", fetchConfigById(request, configId));
        }

        if (parentId != null && parentId > 0)
        {
            model.addAttribute("parentId", parentId);
            model.addAttribute("parent", fetchConfigById(request, parentId));
        }

        return getModulePage("system/dialog/dialogConfigField");
    }

    protected String getMyModulePath()
    {
        return "admin";
    }
}
