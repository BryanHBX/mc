package org.edu.timelycourse.mc.biz.service.school;

import org.edu.timelycourse.mc.biz.entity.school.SchoolBasicInfo;
import org.edu.timelycourse.mc.biz.repository.school.SchoolBasicInfoRepository;
import org.edu.timelycourse.mc.biz.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by x36zhao on 2017/3/17.
 */
@Service
public class SchoolBasicInfoService extends BaseService<SchoolBasicInfo>
{
    private static Logger LOGGER = LoggerFactory.getLogger(SchoolBasicInfoService.class);

    @Autowired
    public SchoolBasicInfoService(SchoolBasicInfoRepository repository)
    {
        super(repository);
    }
}
