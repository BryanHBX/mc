package org.edu.timelycourse.mc.biz.service.school;

import org.edu.timelycourse.mc.biz.model.school.SchoolInfo;
import org.edu.timelycourse.mc.biz.repository.school.SchoolInfoRepository;
import org.edu.timelycourse.mc.biz.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by x36zhao on 2017/3/17.
 */
@Service
public class SchoolInfoService extends BaseService<SchoolInfo>
{
    private static Logger LOGGER = LoggerFactory.getLogger(SchoolInfoService.class);

    @Autowired
    public SchoolInfoService(SchoolInfoRepository repository)
    {
        super(repository);
    }

    @Override
    public SchoolInfo add(SchoolInfo entity)
    {
        entity.setCreationTime(new Date());
        return super.add(entity);
    }

    @Override
    public SchoolInfo update(SchoolInfo entity)
    {
        entity.setLastUpdateTime(new Date());
        return super.update(entity);
    }
}
