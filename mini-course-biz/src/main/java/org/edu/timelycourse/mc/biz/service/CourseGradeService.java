package org.edu.timelycourse.mc.biz.service;

import org.edu.timelycourse.mc.beans.model.CourseClassModel;
import org.edu.timelycourse.mc.biz.repository.CourseClassRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by x36zhao on 2017/3/17.
 */
@Service
public class CourseGradeService extends BaseService<CourseClassModel>
{
    private static Logger LOGGER = LoggerFactory.getLogger(CourseGradeService.class);

    @Autowired
    public CourseGradeService (CourseClassRepository repository)
    {
        super(repository);
    }
}
