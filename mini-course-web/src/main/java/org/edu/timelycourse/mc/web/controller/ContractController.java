package org.edu.timelycourse.mc.web.controller;

import org.edu.timelycourse.mc.biz.enums.EBuiltInConfig;
import org.edu.timelycourse.mc.biz.model.ContractModel;
import org.edu.timelycourse.mc.biz.model.StudentModel;
import org.edu.timelycourse.mc.biz.utils.SecurityContextHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/contract")
public class ContractController extends AbstractController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ContractController.class);

    @RequestMapping("/form")
    public String showContractForm (Model model, HttpServletRequest request)
    {
        model.addAttribute("level",
                fetchConfigByName(request, EBuiltInConfig.C_STUDENT_LEVEL.name()));

        model.addAttribute("products", fetchProducts(request));

        model.addAttribute("course",
                fetchConfigByName(request, EBuiltInConfig.C_COURSE_TYPE.name()));

        return getModulePage("contractForm");
    }

    @RequestMapping("/list")
    public String showContractList (Model model,
                                    @RequestParam(required = false) Integer pageNum,
                                    @RequestParam(required = false) Integer numPerPage,
                                    @ModelAttribute("contract") ContractModel criteria,
                                    HttpServletRequest request)
    {
        criteria.setSchoolId(SecurityContextHelper.getSchoolIdFromPrincipal());
        model.addAttribute("pagingBean", fetchContracts(request, pageNum, numPerPage, criteria));
        model.addAttribute("criteria", criteria);

        return getModulePage("contractList");
    }

    @RequestMapping("/students")
    public String showStudentList (Model model,
                                   @RequestParam(required = false) Integer pageNum,
                                   @RequestParam(required = false) Integer numPerPage,
                                   @ModelAttribute("student") StudentModel criteria,
                                   HttpServletRequest request)
    {
        criteria.setSchoolId(SecurityContextHelper.getSchoolIdFromPrincipal());
        model.addAttribute("criteria", criteria);
        model.addAttribute("pagingBean", fetchStudents(request, pageNum, numPerPage, criteria));

        return getModulePage("studentList");
    }

    protected String getMyModulePath()
    {
        return "contract";
    }
}
