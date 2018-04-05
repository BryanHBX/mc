package org.edu.timelycourse.mc.api.controller.school;

import org.edu.timelycourse.mc.api.controller.BaseController;
import org.edu.timelycourse.mc.biz.entity.school.SchoolInfo;
import org.edu.timelycourse.mc.biz.service.school.SchoolInfoService;
import org.edu.timelycourse.mc.common.entity.ResponseData;
import org.edu.timelycourse.mc.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by x36zhao on 2018/4/3.
 */
@RestController
@RequestMapping("/api/${api.version}/school")
public class SchoolController extends BaseController
{
    private static Logger LOGGER = LoggerFactory.getLogger(SchoolController.class);

    @Autowired
    private SchoolInfoService schoolService;

    @RequestMapping(path="", method= RequestMethod.GET)
    public ResponseData<List<SchoolInfo>> getAllSchools()
    {
        if (LOGGER.isDebugEnabled())
        {
        }
        return new ResponseData<List<SchoolInfo>>(schoolService.getAll());
    }

    @RequestMapping(path="/{schoolId}", method= RequestMethod.GET)
    public ResponseData<SchoolInfo> getSchool(@PathVariable Integer schoolId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter getSchool - [schoolId : %s]", schoolId));

        SchoolInfo entity = schoolService.get(schoolId);
        return new ResponseData<SchoolInfo>(entity != null, entity, "School does not exist");
    }

    @RequestMapping(path="/{schoolId}", method= RequestMethod.DELETE)
    public ResponseData<Integer> deleteSchool(@PathVariable Integer schoolId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter deleteSchool - [schoolId: %d]", schoolId));

        SchoolInfo schoolInfo = this.schoolService.get(schoolId);
        if (schoolInfo != null)
        {
            Integer result = this.schoolService.delete(schoolId);
            return new ResponseData<Integer>(result != null);
        }
        else
        {
            return new ResponseData<Integer>(false, null, "School does not exist");
        }
    }

    @RequestMapping(path="", method= RequestMethod.POST)
    public ResponseData<SchoolInfo> createSchool(@RequestBody SchoolInfo schoolInfo)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter createSchool - [schoolInfo: %s]", schoolInfo));

        try
        {
            return new ResponseData<SchoolInfo>(schoolService.add(schoolInfo));
        }
        catch (ServiceException ex)
        {
            return new ResponseData<SchoolInfo>(false, null, ex.getMessage());
        }
    }

    @RequestMapping(path="/{schoolId}", method= RequestMethod.PATCH)
    public ResponseData<SchoolInfo> updateSchool(
            @PathVariable Integer schoolId,
            @RequestBody SchoolInfo schoolInfo)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter updateSchool - [schoolId: %d, schoolInfo: %s]", schoolId, schoolInfo));

        SchoolInfo entity = this.schoolService.get(schoolId);
        if (entity != null)
        {
            return new ResponseData<SchoolInfo>(this.schoolService.update(schoolInfo));
        }
        else
        {
            return new ResponseData<SchoolInfo>(false, null, "School does not exist");
        }
    }
}
