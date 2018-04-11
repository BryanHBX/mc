package org.edu.timelycourse.mc.web.controller;

import com.google.common.reflect.TypeToken;
import org.edu.timelycourse.mc.biz.enums.EBuiltInConfig;
import org.edu.timelycourse.mc.biz.model.SystemConfigModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contract")
public class ContractController extends AbstractController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ContractController.class);

    @RequestMapping("/form")
    public String showContractForm (Model model)
    {
        model.addAttribute("level", fetchConfigByName(EBuiltInConfig.C_STUDENT_LEVEL.name()));
        model.addAttribute("course", fetchConfigByName(EBuiltInConfig.C_COURSE_TYPE.name()));
        return getModulePage("contractForm");
    }

    @RequestMapping("/list")
    public String showContractList ()
    {
        return getModulePage("contractList");
    }

    @RequestMapping("/students")
    public String showStudentList ()
    {
        return getModulePage("studentList");
    }

    protected String getMyModulePath()
    {
        return "contract";
    }

    private SystemConfigModel fetchConfigByName (String configName)
    {
        return remoteCall("system/config?configName=" + configName,
                new TypeToken<SystemConfigModel>() {}).getData();
    }
}
