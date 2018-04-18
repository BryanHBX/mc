package org.edu.timelycourse.mc.web.controller;

import org.edu.timelycourse.mc.biz.model.ContractModel;
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
@RequestMapping("/finance")
public class FinanceController extends AbstractController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(FinanceController.class);

    @RequestMapping("/contract")
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

    @RequestMapping("/invoice")
    public String showInvoiceList ()
    {
        return getModulePage("invoiceList");
    }

    @RequestMapping("/attendance")
    public String showAttendanceList ()
    {
        return getModulePage("attendanceList");
    }

    @RequestMapping("/consumption")
    public String showConsumptionList ()
    {
        return getModulePage("consumptionList");
    }

    protected String getMyModulePath()
    {
        return "finance";
    }
}
