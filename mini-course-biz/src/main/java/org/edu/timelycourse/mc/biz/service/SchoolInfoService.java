package org.edu.timelycourse.mc.biz.service;

import org.edu.timelycourse.mc.biz.model.SchoolModel;
import org.edu.timelycourse.mc.biz.repository.SchoolInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by x36zhao on 2017/3/17.
 */
@Service
public class SchoolInfoService extends BaseService<SchoolModel>
{
    private static Logger LOGGER = LoggerFactory.getLogger(SchoolInfoService.class);

    @Autowired
    public SchoolInfoService(SchoolInfoRepository repository)
    {
        super(repository);
    }

    @Override
    public SchoolModel add(SchoolModel entity)
    {
        entity.setCreationTime(new Date());
        return super.add(entity);
    }

    @Override
    public SchoolModel update(SchoolModel entity)
    {
        entity.setLastUpdateTime(new Date());
        return super.update(entity);
    }
}
