package org.edu.timelycourse.mc.biz.service;

import org.edu.timelycourse.mc.biz.model.StudentModel;
import org.edu.timelycourse.mc.biz.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by x36zhao on 2017/3/17.
 */
@Service
public class StudentService extends BaseService<StudentModel>
{
    private static Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    public StudentService(StudentRepository repository)
    {
        super(repository);
    }
}
