package org.edu.timelycourse.mc.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.edu.timelycourse.mc.beans.criteria.ContractCriteria;
import org.edu.timelycourse.mc.beans.dto.ContractArrangementDTO;
import org.edu.timelycourse.mc.beans.dto.ContractDTO;
import org.edu.timelycourse.mc.beans.dto.ContractRefundDTO;
import org.edu.timelycourse.mc.beans.dto.ContractTransformDTO;
import org.edu.timelycourse.mc.beans.entity.ResponseData;
import org.edu.timelycourse.mc.beans.model.ContractArrangementModel;
import org.edu.timelycourse.mc.beans.model.ContractModel;
import org.edu.timelycourse.mc.biz.service.ContractArrangementService;
import org.edu.timelycourse.mc.biz.service.ContractService;
import org.edu.timelycourse.mc.biz.utils.Asserts;
import org.edu.timelycourse.mc.biz.utils.SecurityContextHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * Created by x36zhao on 2018/4/3.
 */
@RestController
@RequestMapping("/api/${api.version}/contract/")
@Api(tags = { "课时课表API" })
@PreAuthorize("hasAnyRole('ROLE_CONSULTANT','ROLE_ADMINISTRATOR','ROLE_TREASURER')")
public class ContractCourseController extends BaseController
{
    private static Logger LOGGER = LoggerFactory.getLogger(ContractCourseController.class);

    private static String ARRANGE_TYPE_TEACHER = "teacher";
    private static String ARRANGE_TYPE_SUPERVISOR = "supervisor";

    @Autowired
    private ContractService contractService;

    @Autowired
    private ContractArrangementService arrangementService;

    @ModelAttribute("contractCriteria")
    public ContractCriteria getContractCriteria () { return new ContractCriteria(); }

    @RequestMapping(path="/{contractId}/arrangement", method= RequestMethod.GET)
    @ApiOperation(value = "Get either list of all courses or by given contract criteria")
    public ResponseData getCourseArrangementByContractId(@PathVariable(required = true) Integer contractId,
                                                         @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Enter getCourseArrangementByContractId - [contractId: {}]", contractId);
        }

        return ResponseData.success(ContractArrangementDTO.from(arrangementService.findByContract(contractId)));
    }

    @RequestMapping(path="/{contractId}/arrangement/{id}", method= RequestMethod.DELETE)
    @ApiOperation(value = "Add course arrangement")
    public ResponseData deleteCourseArrangement (@PathVariable(required = true, name = "contractId") Integer contractId,
                                                 @PathVariable(required = true, name = "id") Integer id,
                                                 @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Enter deleteCourseArrangement - [contractId: {}, id: {}]", contractId, id);
        }

        return ResponseData.success(arrangementService.delete(contractId, id));
    }

    @RequestMapping(path="/{contractId}/arrangement", method= RequestMethod.POST)
    @ApiOperation(value = "Add course arrangement")
    public ResponseData addCourseArrangement (@PathVariable(required = true) Integer contractId,
                                              @RequestBody ContractArrangementDTO dto,
                                              @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Enter addCourseArrangement - [contractId: {}, dto: {}]", contractId, dto);
        }

        return ResponseData.success(arrangementService.add(dto, contractId));
    }
}
