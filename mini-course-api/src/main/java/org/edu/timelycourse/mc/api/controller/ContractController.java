package org.edu.timelycourse.mc.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.edu.timelycourse.mc.beans.criteria.ContractCriteria;
import org.edu.timelycourse.mc.beans.dto.ContractDTO;
import org.edu.timelycourse.mc.beans.model.ContractModel;
import org.edu.timelycourse.mc.biz.service.ContractService;
import org.edu.timelycourse.mc.biz.utils.Asserts;
import org.edu.timelycourse.mc.biz.utils.SecurityContextHelper;
import org.edu.timelycourse.mc.common.entity.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * Created by x36zhao on 2018/4/3.
 */
@RestController
@RequestMapping("/api/${api.version}/contract")
@Api(tags = { "合同信息API" })
@PreAuthorize("hasAnyRole('ROLE_CONSULTANT','ROLE_ADMINISTRATOR','ROLE_TREASURER')")
public class ContractController extends BaseController
{
    private static Logger LOGGER = LoggerFactory.getLogger(ContractController.class);

    @Autowired
    private ContractService contractService;

    @ModelAttribute("criteria")
    public ContractCriteria getCriteria () { return new ContractCriteria(); }

    @RequestMapping(path="", method= RequestMethod.GET)
    @ApiOperation(value = "Get either list of all contracts or by given query")
    public ResponseData getContract(@RequestParam(name="pageNum", required = false) Integer pageNum,
                                    @RequestParam(name="pageSize", required = false) Integer pageSize,
                                    @ModelAttribute("criteria") ContractCriteria criteria,
                                    @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Enter getContract - [pageNum: {}, pageSize: {}, criteria: {}]", pageNum, pageSize, criteria);
        }

        criteria.setSchoolId(SecurityContextHelper.getSchoolIdFromPrincipal());
        return ResponseData.success(ContractDTO.from(
                contractService.findByCriteria(criteria, pageNum, pageSize)));

        //return ResponseData.success(ContractDTO.from(contractService.findByPage(
        //        ContractModel.from(criteria), pageNum, pageSize)));
    }

    @RequestMapping(path="/{contractId}", method= RequestMethod.GET)
    @ApiOperation(value = "Get contract by given id")
    public ResponseData getContractById(@PathVariable(required = true) Integer contractId,
                                        @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Enter getContractById - [contractId: {}]", contractId);
        }
        return ResponseData.success(Asserts.assertEntityNotNullById(contractService, contractId));
    }

    @RequestMapping(path="", method= RequestMethod.POST)
    @ApiOperation(value = "Add contract by given entity")
    public ResponseData addContract (@RequestBody ContractModel model,
                                     @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Enter addContract - [model: {}]", model);
        }
        return ResponseData.success(contractService.add(model));
    }

    @RequestMapping(path="/{contractId}", method= RequestMethod.DELETE)
    @ApiOperation(value = "Delete contract by given id")
    public ResponseData deleteContractById (@PathVariable(required = true) Integer contractId,
                                            @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Enter deleteContractById - [contractId: {}]", contractId);
        }
        Asserts.assertEntityNotNullById(contractService, contractId);
        return ResponseData.success(contractService.delete(contractId));
    }

    @RequestMapping(path="/{contractId}", method= RequestMethod.PATCH)
    @ApiOperation(value = "Update contract with respect to the specified id")
    public ResponseData updateContract (@PathVariable(required = true) Integer contractId,
                                        @RequestBody ContractModel model,
                                        @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Enter updateContract - [contractId: {}, model: {}]", contractId, model);
        }
        // in order to avoid overwritten id in the payload
        model.setId(contractId);
        Asserts.assertEntityNotNullById(contractService, contractId);
        return ResponseData.success(this.contractService.update(model));
    }
}
