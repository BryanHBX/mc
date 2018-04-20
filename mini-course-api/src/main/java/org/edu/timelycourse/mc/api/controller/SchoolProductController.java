package org.edu.timelycourse.mc.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.edu.timelycourse.mc.beans.model.SchoolProductModel;
import org.edu.timelycourse.mc.biz.service.SchoolProductService;
import org.edu.timelycourse.mc.biz.utils.Asserts;
import org.edu.timelycourse.mc.biz.utils.SecurityContextHelper;
import org.edu.timelycourse.mc.common.entity.ResponseData;
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
    public ResponseData getProducts(@ModelAttribute("product") SchoolProductModel model,
                                    @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Enter getProducts");
        }

        model.setSchoolId(SecurityContextHelper.getSchoolIdFromPrincipal());
        return ResponseData.success(model.getProductType() != null ?
                productService.findByType(model.getProductType()) : productService.getAll());
    }

    @RequestMapping(path="/{productId}", method= RequestMethod.GET)
    @ApiOperation(value = "Get product by given id")
    public ResponseData getProductById(@PathVariable(required = true) Integer productId,
                                       @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Enter getProductById - [productId: {}]", productId);
        }
        return ResponseData.success(Asserts.assertEntityNotNullById(productService, productId));
    }

    @RequestMapping(path="/{productId}", method= RequestMethod.DELETE)
    @ApiOperation(value = "Delete product by given id")
    public ResponseData deleteProductById (@PathVariable(required = true) Integer productId,
                                           @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Enter deleteProductById - [productId: {}]", productId);
        }
        Asserts.assertEntityNotNullById(productService, productId);
        return ResponseData.success(productService.delete(productId));
    }

    @RequestMapping(path="", method= RequestMethod.POST)
    @ApiOperation(value = "Add product by given entity")
    public ResponseData addProduct (@RequestBody SchoolProductModel model,
                                    @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Enter addProduct - [model: %s]", model);
        }

        return ResponseData.success(productService.add(model));
    }

    @RequestMapping(path="/{productId}", method= RequestMethod.PATCH)
    @ApiOperation(value = "Update product with respect to the specified id")
    public ResponseData updateProduct (@PathVariable(required = true) Integer productId,
                                       @RequestBody SchoolProductModel model,
                                       @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Enter updateProduct - [productId: {}, model: {}]", productId, model);
        }
        // in order to avoid overwritten id in request body
        model.setId(productId);
        Asserts.assertEntityNotNullById(productService, productId);
        return ResponseData.success(this.productService.update(model));
    }
}