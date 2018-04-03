package org.edu.energycourse.mc.biz.service.school;

import org.edu.energycourse.mc.biz.entity.school.SchoolInfo;
import org.edu.energycourse.mc.biz.repository.BaseRepository;
import org.edu.energycourse.mc.biz.repository.school.SchoolInfoRepository;
import org.edu.energycourse.mc.common.exception.ServiceException;
import org.edu.energycourse.mc.biz.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
