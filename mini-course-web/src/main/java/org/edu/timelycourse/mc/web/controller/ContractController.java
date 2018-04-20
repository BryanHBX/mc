package org.edu.timelycourse.mc.web.controller;

import org.edu.timelycourse.mc.beans.criteria.ContractCriteria;
import org.edu.timelycourse.mc.beans.criteria.StudentCriteria;
import org.edu.timelycourse.mc.beans.dto.ContractDTO;
import org.edu.timelycourse.mc.beans.enums.EBuiltInConfig;
import org.edu.timelycourse.mc.beans.model.ContractModel;
import org.edu.timelycourse.mc.beans.model.StudentModel;
import org.edu.timelycourse.mc.biz.utils.SecurityContextHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/contract")
public class ContractController extends AbstractController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ContractController.class);

    @ModelAttribute("contractCriteria")
    private ContractCriteria getContractCriteria () { return new ContractCriteria(); }

    @ModelAttribute("studentCriteria")
    private StudentCriteria getStudentCriteria () { return new StudentCriteria(); }

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
                                    @ModelAttribute("contractCriteria") ContractCriteria criteria,
                                    HttpServletRequest request)
    {
        model.addAttribute("pagingBean", fetchContracts(request, pageNum, numPerPage));
        model.addAttribute("criteria", criteria);

        return getModulePage("contractList");
    }

    @RequestMapping("/students")
    public String showStudentList (Model model,
                                   @RequestParam(required = false) Integer pageNum,
                                   @RequestParam(required = false) Integer numPerPage,
                                   @ModelAttribute("studentCriteria") StudentCriteria criteria,
                                   HttpServletRequest request)
    {
        model.addAttribute("criteria", criteria);
        model.addAttribute("pagingBean", fetchStudents(request, pageNum, numPerPage));

        model.addAttribute("level",
                fetchConfigByName(request, EBuiltInConfig.C_STUDENT_LEVEL.name()));

        return getModulePage("studentList");
    }

    protected String getMyModulePath()
    {
        return "contract";
    }
}
