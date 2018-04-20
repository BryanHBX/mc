package org.edu.timelycourse.mc.biz.service;

import org.edu.timelycourse.mc.beans.model.StudentAttendanceModel;
import org.edu.timelycourse.mc.biz.repository.StudentAttendanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by x36zhao on 2017/3/17.
 */
@Service
public class CourseArrangementService extends BaseService<StudentAttendanceModel>
{
    private static Logger LOGGER = LoggerFactory.getLogger(CourseArrangementService.class);

    @Autowired
    public CourseArrangementService (StudentAttendanceRepository repository)
    {
        super(repository);
    }
}
