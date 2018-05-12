package org.edu.timelycourse.mc.web.controller;

import org.edu.timelycourse.mc.beans.criteria.InvoiceCriteria;
import org.edu.timelycourse.mc.beans.dto.ContractInvoiceStatDTO;
import org.edu.timelycourse.mc.beans.entity.ResponseData;
import org.edu.timelycourse.mc.biz.utils.SecurityContextHelper;
import org.edu.timelycourse.mc.export.report.ContractInvoiceReportBuilder;
import org.edu.timelycourse.mc.web.utils.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by marco on 2018/5/12
 */

@RestController
@RequestMapping("/export")
public class ExportController extends AbstractController
{
    private static Logger LOGGER = LoggerFactory.getLogger(ExportController.class);

    @RequestMapping(path="/invoice", method= RequestMethod.POST)
    public ResponseData exportInvoice(@ModelAttribute("invoiceCriteria") InvoiceCriteria criteria,
                                      HttpServletRequest request)
    {
        try
        {
            String reportName = "收据记录导出表";
            ContractInvoiceReportBuilder reportBuilder = new ContractInvoiceReportBuilder();

            criteria.setSchoolId(SecurityContextHelper.getSchoolIdFromPrincipal());
            if (LOGGER.isDebugEnabled())
            {
                LOGGER.debug("Enter exportInvoice - [criteria: {}]", criteria);
            }

            ContractInvoiceStatDTO invoiceStatDTO = restServiceCaller.findInvoicesByPage(request, 1, Integer.MAX_VALUE);
            byte[] bytes = reportBuilder.build(reportName,invoiceStatDTO.getInvoices().getItems());
            return ResponseData.success(reportBuilder.write(PathUtils.getAssetTempPath(), reportName, bytes));
        }
        catch (Exception ex)
        {
            return  ResponseData.failure(ex.getMessage());
        }
    }

    @ModelAttribute("invoiceCriteria")
    public InvoiceCriteria getCriteria()
    {
        return new InvoiceCriteria();
    }

    @Override
    protected String getMyModulePath()
    {
        return null;
    }

    @Override
    protected String getModuleName()
    {
        return null;
    }
}
