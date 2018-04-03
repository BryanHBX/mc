package org.edu.energycourse.mc.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/finance")
public class FinanceController extends AbstractController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(FinanceController.class);

    @RequestMapping("/contract")
    public String showContractList ()
    {
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
