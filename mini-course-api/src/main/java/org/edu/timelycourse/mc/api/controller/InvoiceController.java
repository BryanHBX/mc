package org.edu.timelycourse.mc.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.edu.timelycourse.mc.beans.criteria.ContractInvoiceCriteria;
import org.edu.timelycourse.mc.beans.dto.InvoiceDTO;
import org.edu.timelycourse.mc.beans.model.ContractInvoiceModel;
import org.edu.timelycourse.mc.biz.service.InvoiceService;
import org.edu.timelycourse.mc.biz.utils.Asserts;
import org.edu.timelycourse.mc.beans.entity.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by x36zhao on 2018/4/3.
 */
@RestController
@RequestMapping("/api/${api.version}/invoice")
@Api(tags = { "合同收据API" })
public class InvoiceController extends BaseController
{
    private static Logger LOGGER = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    private InvoiceService invoiceService;

    @ModelAttribute("criteria")
    public ContractInvoiceCriteria getCriteria()
    {
        return new ContractInvoiceCriteria();
    }

    @RequestMapping(path="", method= RequestMethod.GET)
    @ApiOperation(value = "Get either list of all invoices or by given query")
    @PreAuthorize("hasAnyRole('ROLE_TREASURER','ROLE_ADMINISTRATOR')")
    public ResponseData getInvoice(@RequestParam(name="pageNum", required = false) Integer pageNum,
                                   @RequestParam(name="pageSize", required = false) Integer pageSize,
                                   @ModelAttribute("criteria") ContractInvoiceCriteria criteria,
                                   @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Enter getInvoice - [pageNum: {}, pageSize: {}, criteria: {}]", pageNum, pageSize, criteria);
        }

        return ResponseData.success(InvoiceDTO.from(invoiceService.findByCriteria(criteria, pageNum, pageSize)));
        //return ResponseData.success(invoiceService.findByPage(ContractInvoiceModel.from(criteria), pageNum, pageSize));
    }

    @RequestMapping(path="/{invoiceId}", method= RequestMethod.GET)
    @ApiOperation(value = "Get contract by given id")
    @PreAuthorize("hasAnyRole('ROLE_TREASURER','ROLE_ADMINISTRATOR')")
    public ResponseData getInvoiceById(@PathVariable(required = true) Integer invoiceId,
                                       @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug(String.format("Enter getInvoiceById - [invoiceId: %d]", invoiceId));
        }
        return ResponseData.success(Asserts.assertEntityNotNullById(invoiceService, invoiceId));
    }

    @RequestMapping(path="", method= RequestMethod.POST)
    @ApiOperation(value = "Add invoice by given entity")
    @PreAuthorize("hasAnyRole('ROLE_TREASURER','ROLE_ADMINISTRATOR', 'ROLE_CONSULTANT')")
    public ResponseData addInvoice (@RequestBody ContractInvoiceModel model,
                                    @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug(String.format("Enter addInvoice - [model: %s]", model));
        }
        return ResponseData.success(invoiceService.add(model));
    }

    @RequestMapping(path="/{invoiceId}", method= RequestMethod.DELETE)
    @ApiOperation(value = "Delete invoice by given id")
    @PreAuthorize("hasAnyRole('ROLE_TREASURER','ROLE_ADMINISTRATOR')")
    public ResponseData deleteContractById (@PathVariable(required = true) Integer invoiceId,
                                            @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug(String.format("Enter deleteContractById - [invoiceId: %d]", invoiceId));
        }
        Asserts.assertEntityNotNullById(invoiceService, invoiceId);
        return ResponseData.success(invoiceService.delete(invoiceId));
    }

    @RequestMapping(path="/{invoiceId}", method= RequestMethod.PATCH)
    @ApiOperation(value = "Update invoice with respect to the specified id")
    @PreAuthorize("hasAnyRole('ROLE_TREASURER','ROLE_ADMINISTRATOR')")
    public ResponseData updateInvoice (@PathVariable(required = true) Integer invoiceId,
                                       @RequestBody ContractInvoiceModel model,
                                       @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug(String.format("Enter updateInvoice - [invoiceId: %d, model: %s]", invoiceId, model));
        }
        // in order to avoid overwritten id in the payload
        model.setId(invoiceId);
        Asserts.assertEntityNotNullById(invoiceService, invoiceId);
        return ResponseData.success(this.invoiceService.update(model));
    }
}
