package org.edu.timelycourse.mc.web.controller;

import com.google.common.reflect.TypeToken;
import org.edu.timelycourse.mc.biz.enums.EBuiltInConfig;
import org.edu.timelycourse.mc.biz.model.SystemConfigModel;
import org.edu.timelycourse.mc.biz.model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/school")
public class SchoolController extends AbstractController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SchoolController.class);

    @RequestMapping("/info")
    public String showSchoolInfo ()
    {
        return getModulePage("schoolInfo");
    }

    @RequestMapping("/product")
    public String showSchoolProduct ()
    {
        return getModulePage("schoolProduct");
    }

    @RequestMapping("/member")
    public String showSchoolMember ()
    {
        return getModulePage("schoolMember");
    }

    @RequestMapping("/member/dialog")
    public String showSchoolMemberDialog (
            Model model, @RequestParam(required = false, value = "id")  Integer memberId)
    {
        if (memberId != null && memberId > 0)
        {
            model.addAttribute("member", fetchMemberById(memberId));
        }

        model.addAttribute("grades", fetchConfigByName(EBuiltInConfig.C_GRADE.name()));
        model.addAttribute("subjects", fetchConfigByName(EBuiltInConfig.C_SUBJECT.name()));

        return getModulePage("dialog/dialogSchoolMember");
    }

    @RequestMapping("/consult")
    public String showSchoolConsult ()
    {
        return getModulePage("schoolConsult");
    }

    protected String getMyModulePath()
    {
        return "school";
    }

    private UserModel fetchMemberById (Integer memberId)
    {
        return remoteCall("member/" + memberId, new TypeToken<UserModel>() {}).getData();
    }

    private SystemConfigModel fetchConfigByName (String configName)
    {
        return remoteCall("system/config?configName=" + configName, new TypeToken<SystemConfigModel>() {}).getData();
    }
}
