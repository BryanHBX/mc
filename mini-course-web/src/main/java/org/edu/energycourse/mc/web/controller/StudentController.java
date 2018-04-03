package org.edu.energycourse.mc.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentController extends AbstractController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    @RequestMapping("/contractForm")
    public String showContractForm ()
    {
        return getModulePage("contractForm");
    }

    @RequestMapping("/contractList")
    public String showContractList ()
    {
        return getModulePage("contractList");
    }

    @RequestMapping("/studentList")
    public String showStudentList ()
    {
        return getModulePage("studentList");
    }

    protected String getMyModulePath()
    {
        return "student";
    }
}
