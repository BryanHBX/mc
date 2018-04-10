package org.edu.timelycourse.mc.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.edu.timelycourse.mc.biz.model.InvoiceModel;
import org.edu.timelycourse.mc.biz.service.InvoiceService;
import org.edu.timelycourse.mc.biz.utils.Asserts;
import org.edu.timelycourse.mc.common.entity.ResponseData;
import org.edu.timelycourse.mc.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(path="", method= RequestMethod.GET)
    @ApiOperation(value = "Get either list of all invoices or by given query")
    public ResponseData getInvoice(@RequestParam(required = false, value = "query") String query)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Enter getInvoice - [query: %s]", query);

        try
        {
            return ResponseData.success(invoiceService.getAll());
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="/{invoiceId}", method= RequestMethod.GET)
    @ApiOperation(value = "Get contract by given id")
    public ResponseData getInvoiceById(@PathVariable(required = true) Integer invoiceId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter getInvoiceById - [invoiceId: %d]", invoiceId));

        try
        {
            return ResponseData.success(Asserts.assertEntityNotNullById(invoiceService, invoiceId));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="", method= RequestMethod.POST)
    @ApiOperation(value = "Add invoice by given entity")
    public ResponseData addInvoice (@RequestBody InvoiceModel model)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter addInvoice - [model: %s]", model));

        try
        {
            return ResponseData.success(invoiceService.add(model));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="/{invoiceId}", method= RequestMethod.DELETE)
    @ApiOperation(value = "Delete invoice by given id")
    public ResponseData deleteContractById (@PathVariable(required = true) Integer invoiceId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter deleteContractById - [invoiceId: %d]", invoiceId));

        try
        {
            Asserts.assertEntityNotNullById(invoiceService, invoiceId);
            return ResponseData.success(invoiceService.delete(invoiceId));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="/{invoiceId}", method= RequestMethod.PATCH)
    @ApiOperation(value = "Update invoice with respect to the specified id")
    public ResponseData updateInvoice (
            @PathVariable(required = true) Integer invoiceId,
            @RequestBody InvoiceModel model)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter updateInvoice - [invoiceId: %d, model: %s]", invoiceId, model));

        try
        {
            Asserts.assertEntityNotNullById(invoiceService, invoiceId);

            // in order to avoid overwritten id in the payload
            model.setId(invoiceId);

            return ResponseData.success(this.invoiceService.update(model));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }
}
