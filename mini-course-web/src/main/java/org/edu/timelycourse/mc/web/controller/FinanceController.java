package org.edu.timelycourse.mc.web.controller;

import org.edu.timelycourse.mc.beans.criteria.ContractCriteria;
import org.edu.timelycourse.mc.beans.criteria.InvoiceCriteria;
import org.edu.timelycourse.mc.beans.dto.ContractDTO;
import org.edu.timelycourse.mc.beans.entity.ResponseData;
import org.edu.timelycourse.mc.biz.utils.SecurityContextHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
                                    @ModelAttribute("contractCriteria") ContractCriteria criteria,
                                    HttpServletRequest request)
    {
        model.addAttribute("pagingBean", restServiceCaller.findContractsByPage(request, pageNum, numPerPage));
        model.addAttribute("criteria", criteria);
        model.addAttribute("module", getModuleName());
        return getModulePage("contract/contractList");
    }

    @RequestMapping(value = "/contract/transfer", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData transferContract (Model model)
    {
        return null;
    }

    @RequestMapping("/invoice")
    public String showInvoiceList (Model model,
                                   @RequestParam(required = false) Integer pageNum,
                                   @RequestParam(required = false) Integer numPerPage,
                                   @ModelAttribute("invoiceCriteria") InvoiceCriteria criteria,
                                   HttpServletRequest request)
    {
        model.addAttribute("stat", restServiceCaller.findInvoicesByPage(request, pageNum, numPerPage));
        model.addAttribute("criteria", criteria);
        model.addAttribute("module", getModuleName());
        return getModulePage("invoice/invoiceList");
    }

    @RequestMapping("/attendance")
    public String showAttendanceList (Model model)
    {
        model.addAttribute("module", getModuleName());
        return getModulePage("attendance/attendanceList");
    }

    @RequestMapping("/consumption")
    public String showConsumptionList ()
    {
        return getModulePage("consumptionList");
    }

    @Override
    protected String getMyModulePath()
    {
        return null;
    }

    @Override
    protected String getModuleName()
    {
        return "finance";
    }

    @ModelAttribute("contractCriteria")
    private ContractCriteria getContractCriteria () { return new ContractCriteria(); }

    @ModelAttribute("invoiceCriteria")
    private InvoiceCriteria getInvoiceCriteria () { return new InvoiceCriteria(); }
}
