package org.edu.timelycourse.mc.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.edu.timelycourse.mc.beans.criteria.ContractClazzCriteria;
import org.edu.timelycourse.mc.beans.criteria.ContractCriteria;
import org.edu.timelycourse.mc.beans.dto.ContractClazzDTO;
import org.edu.timelycourse.mc.beans.entity.ResponseData;
import org.edu.timelycourse.mc.beans.paging.PagingBean;
import org.edu.timelycourse.mc.biz.service.ContractArrangementService;
import org.edu.timelycourse.mc.biz.service.ContractClazzService;
import org.edu.timelycourse.mc.biz.service.UserService;
import org.edu.timelycourse.mc.biz.utils.SecurityContextHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by marco on 2018/4/29
 */
@RestController
@RequestMapping("/api/${api.version}/teacher")
@Api(tags = { "教师信息API" })
@PreAuthorize("hasAnyRole('ROLE_TEACHER','ROLE_ADMINISTRATOR','ROLE_TREASURER')")
public class TeacherController extends BaseController
{
    private static Logger LOGGER = LoggerFactory.getLogger(TeacherController.class);

    @Autowired
    private ContractArrangementService arrangementService;

    @Autowired
    private ContractClazzService clazzService;

    @Autowired
    private UserService userService;

    @RequestMapping(path="{teacherId}/course", method= RequestMethod.GET)
    @ApiOperation(value = "Get list of contract arrangements by teacher id")
    public ResponseData getCoursegByTeacherId(@PathVariable(required = true, name = "teacherId") Integer teacherId,
                                              @RequestParam(name="pageNum", required = false) Integer pageNum,
                                              @RequestParam(name="pageSize", required = false) Integer pageSize,
                                              @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Enter getCoursegByTeacherId - [teacherId: {}, pageNum: {}, pageSize: {}, criteria: {}]", teacherId, pageNum, pageSize);
        }

        return null;
    }

    @RequestMapping(path="{teacherId}/clazz", method= RequestMethod.GET)
    @ApiOperation(value = "Get list of contract arrangements by teacher id")
    public ResponseData<PagingBean<ContractClazzDTO>> getClazzByTeacherId(
            @PathVariable(required = true, name = "teacherId") Integer teacherId,
            @RequestParam(name="pageNum", required = false) Integer pageNum,
            @RequestParam(name="pageSize", required = false) Integer pageSize,
            @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Enter getClazzByTeacherId - [teacherId: {}, pageNum: {}, pageSize: {}]",
                    teacherId, pageNum, pageSize);
        }

        ContractClazzCriteria criteria = new ContractClazzCriteria();
        criteria.setSchoolId(SecurityContextHelper.getSchoolIdFromPrincipal());
        criteria.setTeacherId(teacherId);

        return ResponseData.success(ContractClazzDTO.from(clazzService.findByCriteria(criteria, pageNum, pageSize)));
    }

    @ModelAttribute("clazzCriteria")
    public ContractClazzCriteria getCriteria () { return new ContractClazzCriteria(); }
}
