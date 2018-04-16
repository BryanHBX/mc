package org.edu.timelycourse.mc.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.edu.timelycourse.mc.biz.model.SchoolModel;
import org.edu.timelycourse.mc.biz.service.SchoolService;
import org.edu.timelycourse.mc.biz.utils.Asserts;
import org.edu.timelycourse.mc.common.entity.ResponseData;
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
    private SchoolService schoolService;

    @ModelAttribute("school")
    public SchoolModel getModel()
    {
        return new SchoolModel ();
    }

    @RequestMapping(path="", method= RequestMethod.GET)
    @ApiOperation(value = "Get either list of all schools or by given query")
    public ResponseData getSchools(@RequestParam(name="pageNum", required = false) Integer pageNum,
                                   @RequestParam(name="pageSize", required = false) Integer pageSize,
                                   @ModelAttribute("school") SchoolModel model,
                                   @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Enter getSchools - [pageNum: {}, pageSize: {}, schoolInfo: {}]", pageNum, pageSize, model);
        }

        return ResponseData.success(schoolService.findByPage(model, pageNum, pageSize));
    }

    @RequestMapping(path="/{id}", method= RequestMethod.GET)
    @ApiOperation(value = "Get school by given id")
    public ResponseData getSchool(@PathVariable(required = true) Integer id,
                                  @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Enter getSchool - [schoolId : {}]", id);
        }
        return ResponseData.success(Asserts.assertEntityNotNullById(schoolService, id));
    }

    @RequestMapping(path="/{id}", method= RequestMethod.DELETE)
    @ApiOperation(value = "Delete school by given id")
    public ResponseData deleteSchool(@PathVariable(required = true) Integer id,
                                     @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Enter deleteSchool - [schoolId: {}]", id);
        }
        Asserts.assertEntityNotNullById(schoolService, id);
        return ResponseData.success(schoolService.delete(id));
    }

    @RequestMapping(path="", method= RequestMethod.POST)
    @ApiOperation(value = "Add school by given entity")
    public ResponseData addSchool(@RequestBody SchoolModel model,
                                  @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Enter addSchool - [schoolInfo: {}]", model);
        }
        return ResponseData.success(schoolService.add(model));
    }

    @RequestMapping(path="/{id}", method= RequestMethod.PATCH)
    @ApiOperation(value = "Update school with respect to the specified id")
    public ResponseData updateSchool(@PathVariable(required = true) Integer id,
                                     @RequestBody SchoolModel model,
                                     @RequestHeader(name = "Authorization") String auth)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Enter updateSchool - [id: {}, schoolInfo: {}]", id, model);
        }
        model.setId(id);
        Asserts.assertEntityNotNullById(schoolService, id);
        return ResponseData.success(this.schoolService.update(model));
    }
}
