package org.edu.timelycourse.mc.api.controller.school;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.edu.timelycourse.mc.api.controller.BaseController;
import org.edu.timelycourse.mc.biz.model.school.SchoolInfo;
import org.edu.timelycourse.mc.biz.service.school.SchoolInfoService;
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
@RequestMapping("/api/${api.version}/school")
@Api(tags = { "学校信息API" })
public class SchoolController extends BaseController
{
    private static Logger LOGGER = LoggerFactory.getLogger(SchoolController.class);

    @Autowired
    private SchoolInfoService schoolService;

    @RequestMapping(path="", method= RequestMethod.GET)
    @ApiOperation(value = "Get either list of all schools or by given query")
    public ResponseData getAllSchools()
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Enter getAllSchools");

        try
        {
            return ResponseData.success(schoolService.getAll());
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="/{id}", method= RequestMethod.GET)
    @ApiOperation(value = "Get school by given id")
    public ResponseData getSchool(@PathVariable(required = true) Integer id)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter getSchool - [schoolId : %s]", id));

        try
        {
            SchoolInfo entity = (SchoolInfo) Asserts.assertEntityNotNullById(schoolService, id);
            return ResponseData.success(entity);
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="/{id}", method= RequestMethod.DELETE)
    @ApiOperation(value = "Delete school by given id")
    public ResponseData deleteSchool(@PathVariable(required = true) Integer id)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter deleteSchool - [schoolId: %d]", id));

        try
        {
            Asserts.assertEntityNotNullById(schoolService, id);
            return ResponseData.success(schoolService.delete(id));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="", method= RequestMethod.POST)
    @ApiOperation(value = "Add school by given entity")
    public ResponseData addSchool(@RequestBody SchoolInfo schoolInfo)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter addSchool - [schoolInfo: %s]", schoolInfo));

        try
        {
            return ResponseData.success(schoolService.add(schoolInfo));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="/{id}", method= RequestMethod.PATCH)
    @ApiOperation(value = "Update school with respect to the specified id")
    public ResponseData updateSchool(
            @PathVariable(required = true) Integer id,
            @RequestBody SchoolInfo schoolInfo)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter updateSchool - [id: %d, schoolInfo: %s]", id, schoolInfo));

        try
        {
            SchoolInfo entity = (SchoolInfo) Asserts.assertEntityNotNullById(schoolService, id);

            schoolInfo.setId(id);
            schoolInfo.setCreationTime(entity.getCreationTime());
            return ResponseData.success(this.schoolService.update(schoolInfo));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }
}
