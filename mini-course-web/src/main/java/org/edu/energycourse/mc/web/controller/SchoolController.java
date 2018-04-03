package org.edu.energycourse.mc.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/consult")
    public String showSchoolConsult ()
    {
        return getModulePage("schoolConsult");
    }

    protected String getMyModulePath()
    {
        return "school";
    }
}
