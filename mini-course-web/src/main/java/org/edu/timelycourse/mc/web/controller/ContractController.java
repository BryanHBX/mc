package org.edu.timelycourse.mc.web.controller;

import org.edu.timelycourse.mc.beans.criteria.ContractCriteria;
import org.edu.timelycourse.mc.beans.criteria.StudentCriteria;
import org.edu.timelycourse.mc.beans.enums.EBuiltInConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
                findConfigByName(request, EBuiltInConfig.C_STUDENT_LEVEL.name()));

        model.addAttribute("products", getAllProducts(request));

        model.addAttribute("course",
                findConfigByName(request, EBuiltInConfig.C_COURSE_TYPE.name()));

        return getModulePage("contract/form/formContract");
    }

    @RequestMapping("/list")
    public String showContractList (Model model,
                                    @RequestParam(required = false) Integer pageNum,
                                    @RequestParam(required = false) Integer numPerPage,
                                    @ModelAttribute("contractCriteria") ContractCriteria criteria,
                                    HttpServletRequest request)
    {
        model.addAttribute("pagingBean", findContractsByPage(request, pageNum, numPerPage));
        model.addAttribute("criteria", criteria);
        model.addAttribute("module", getModuleName());
        return getModulePage("contract/contractList");
    }

    @RequestMapping("/{id}")
    public String showContract (Model model,
                                @PathVariable(required = true, name = "id") Integer contractId,
                                @RequestParam(required = false, name = "op") String operation,
                                HttpServletRequest request)
    {
        model.addAttribute("level", findConfigByName(request, EBuiltInConfig.C_STUDENT_LEVEL.name()));
        model.addAttribute("products", getAllProducts(request));
        model.addAttribute("course", findConfigByName(request, EBuiltInConfig.C_COURSE_TYPE.name()));
        model.addAttribute("entity", findContractById(request, contractId));
        model.addAttribute("op", operation);

        return getModulePage("contract/dialog/dialogContract");
    }

    @RequestMapping("/students")
    public String showStudentList (Model model,
                                   @RequestParam(required = false) Integer pageNum,
                                   @RequestParam(required = false) Integer numPerPage,
                                   @ModelAttribute("studentCriteria") StudentCriteria criteria,
                                   HttpServletRequest request)
    {
        model.addAttribute("criteria", criteria);
        model.addAttribute("pagingBean", findStudentsByPage(request, pageNum, numPerPage));

        model.addAttribute("level",
                findConfigByName(request, EBuiltInConfig.C_STUDENT_LEVEL.name()));

        return getModulePage("student/studentList");
    }

    protected String getMyModulePath()
    {
        return null;
    }

    @Override
    protected String getModuleName()
    {
        return "contract";
    }

    @ModelAttribute("contractCriteria")
    private ContractCriteria getContractCriteria () { return new ContractCriteria(); }

    @ModelAttribute("studentCriteria")
    private StudentCriteria getStudentCriteria () { return new StudentCriteria(); }
}
