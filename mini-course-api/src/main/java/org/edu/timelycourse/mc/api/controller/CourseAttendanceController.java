package org.edu.timelycourse.mc.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.edu.timelycourse.mc.beans.criteria.CourseAttendanceCriteria;
import org.edu.timelycourse.mc.beans.dto.ContractAttendanceDTO;
import org.edu.timelycourse.mc.beans.entity.ResponseData;
import org.edu.timelycourse.mc.biz.service.ContractAttendanceService;
import org.edu.timelycourse.mc.biz.service.UserService;
import org.edu.timelycourse.mc.biz.utils.SecurityContextHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by marco on 2018/4/29
 */
@RestController
@RequestMapping("/api/${api.version}/attendance")
@Api(tags = { "课时考勤API" })
public class CourseAttendanceController extends BaseController
{
    private static Logger LOGGER = LoggerFactory.getLogger(CourseAttendanceController.class);

    @Autowired
    private ContractAttendanceService attendanceService;

    @Autowired
    private UserService userService;

    @RequestMapping(path="/{contractId}", method= RequestMethod.GET)
    @ApiOperation(value = "Get attendance by given contract id")
    public ResponseData<ContractAttendanceDTO> getAttendanceByContractId(@PathVariable(required = true) Integer contractId,
                                                                         @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Enter getAttendanceByContractId - [contractId: {}]", contractId);
        }
        return null;
    }

    @RequestMapping(path="/{contractId}", method= RequestMethod.POST)
    @ApiOperation(value = "Add attendance by given entity")
    public ResponseData<ContractAttendanceDTO> addAttendance (@RequestBody ContractAttendanceDTO model,
                                                              @RequestHeader(name = "Authorization") String auth)
    {
        model.setSchoolId(SecurityContextHelper.getSchoolIdFromPrincipal());

        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Enter addAttendance - [model: {}]", model);
        }
        return null;
    }

    @ModelAttribute("attendanceCriteria")
    public CourseAttendanceCriteria getCriteria () { return new CourseAttendanceCriteria(); }
}
