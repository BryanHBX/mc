package org.edu.timelycourse.mc.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.edu.timelycourse.mc.biz.model.SchoolProductModel;
import org.edu.timelycourse.mc.biz.service.SchoolProductService;
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
@RequestMapping("/api/${api.version}/product")
@Api(tags = { "学校产品API" })
public class SchoolProductController extends BaseController
{
    private static Logger LOGGER = LoggerFactory.getLogger(SchoolProductController.class);

    @Autowired
    private SchoolProductService productService;

    @ModelAttribute("product")
    public SchoolProductModel getModel()
    {
        return new SchoolProductModel ();
    }


    @RequestMapping(path="", method= RequestMethod.GET)
    @ApiOperation(value = "Get either list of all products or by given query")
    public ResponseData getProducts(@ModelAttribute("product") SchoolProductModel model)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Enter getProducts");

        try
        {
            if (model.getProductType() != null)
            {
                return ResponseData.success(productService.findByType(model.getProductType()));
            }

            return ResponseData.success(productService.getAll());
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="/{productId}", method= RequestMethod.GET)
    @ApiOperation(value = "Get product by given id")
    public ResponseData getProductById(@PathVariable(required = true) Integer productId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Enter getProductById - [productId: {}]", productId);

        try
        {
            return ResponseData.success(Asserts.assertEntityNotNullById(productService, productId));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="/{productId}", method= RequestMethod.DELETE)
    @ApiOperation(value = "Delete product by given id")
    public ResponseData deleteProductById (@PathVariable(required = true) Integer productId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Enter deleteProductById - [productId: {}]", productId);

        try
        {
            Asserts.assertEntityNotNullById(productService, productId);
            return ResponseData.success(productService.delete(productId));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="", method= RequestMethod.POST)
    @ApiOperation(value = "Add product by given entity")
    public ResponseData addProduct (@RequestBody SchoolProductModel model)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Enter addProduct - [model: %s]", model);

        try
        {
            return ResponseData.success(productService.add(model));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="/{productId}", method= RequestMethod.PATCH)
    @ApiOperation(value = "Update product with respect to the specified id")
    public ResponseData updateProduct (
            @PathVariable(required = true) Integer productId,
            @RequestBody SchoolProductModel model)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Enter updateProduct - [productId: {}, model: {}]", productId, model);

        try
        {
            Asserts.assertEntityNotNullById(productService, productId);

            // in order to avoid overwritten id in request body
            model.setId(productId);

            return ResponseData.success(this.productService.update(model));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }
}