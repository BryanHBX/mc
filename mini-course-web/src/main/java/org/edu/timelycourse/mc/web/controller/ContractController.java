package org.edu.timelycourse.mc.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contract")
public class ContractController extends AbstractController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ContractController.class);

    @RequestMapping("/form")
    public String showContractForm ()
    {
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
}
