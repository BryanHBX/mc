package org.edu.timelycourse.mc.biz.service;

import org.edu.timelycourse.mc.beans.model.StudentModel;
import org.edu.timelycourse.mc.beans.paging.PagingBean;
import org.edu.timelycourse.mc.biz.repository.SchoolRepository;
import org.edu.timelycourse.mc.biz.repository.StudentRepository;
import org.edu.timelycourse.mc.biz.repository.SystemConfigRepository;
import org.edu.timelycourse.mc.biz.utils.Asserts;
import org.edu.timelycourse.mc.biz.utils.SecurityContextHelper;
import org.edu.timelycourse.mc.common.exception.ServiceException;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by x36zhao on 2017/3/17.
 */
@Service
public class StudentService extends BaseService<StudentModel>
{
    private static Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

    private StudentRepository studentRepository;
    private SchoolRepository schoolRepository;
    private SystemConfigRepository configRepository;

    @Autowired
    public StudentService(StudentRepository repository,
                          SchoolRepository schoolRepository,
                          SystemConfigRepository configRepository)
    {
        super(repository);
        this.studentRepository = repository;
        this.schoolRepository = schoolRepository;
        this.configRepository = configRepository;
    }

    @Override
    public PagingBean<StudentModel> findByPage(StudentModel entity, Integer pageNum, Integer pageSize)
    {
        entity.setSchoolId(SecurityContextHelper.getSchoolIdFromPrincipal());
        return super.findByPage(entity, pageNum, pageSize);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StudentModel add (StudentModel entity)
    {
        entity.setSchoolId(SecurityContextHelper.getSchoolIdFromPrincipal());
        if (entity.isValidInput())
        {
            // check if school exists
            Asserts.assertEntityNotNullById(schoolRepository, entity.getSchoolId());

            // check if wx id exists already or not
            if (studentRepository.getByWxId(entity.getWxId()) != null)
            {
                throw new ServiceException(String.format(
                        "Student with wxId: %s already exists", entity.getWxId()));
            }

            // check if configs exists in db
            if (!isConfigExists(entity.getLevelId(), entity.getSubLevelId(),
                    entity.getCourseId(), entity.getSubCourseId()))
            {
                throw new ServiceException(String.format(
                        "Config does not exist - [levelId: %d, subLevelId: %d, courseId: %d, subCourseId: %d]",
                        entity.getLevelId(), entity.getSubLevelId(),
                        entity.getCourseId(), entity.getSubCourseId()));
            }

            entity.setCreationTime(new Date());
            return super.add(entity);
        }

        throw new ServiceException(String.format("Invalid model data to add, %s", entity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StudentModel update (StudentModel entity)
    {
        if (entity.isValidInput() && EntityUtils.isValidEntityId(entity.getId()))
        {
            StudentModel entityInDb = (StudentModel) Asserts.assertEntityNotNullById(repository, entity.getId());

            // check if any with respect to school
            if (entityInDb.getSchoolId() != entity.getSchoolId())
            {
                throw new ServiceException(String.format(
                        "It's not allowed to change the school from %d to %d",
                        entityInDb.getSchoolId(), entity.getSchoolId()));
            }

            // check if configs exists in db
            if (!isConfigExists(entity.getLevelId(), entity.getSubLevelId(),
                    entity.getCourseId(), entity.getSubCourseId()))
            {
                throw new ServiceException(String.format(
                        "Config does not exist - [levelId: %d, subLevelId: %d, courseId: %d, subCourseId: %d]",
                        entity.getLevelId(), entity.getSubLevelId(),
                        entity.getCourseId(), entity.getSubCourseId()));
            }

            entityInDb = studentRepository.getByWxId(entity.getWxId());
            if (entityInDb != null && !entityInDb.getId().equals(entity.getId()))
            {
                throw new ServiceException(String.format(
                        "Student already exists with [wxId: %s]", entity.getWxId()));
            }

            entity.setLastUpdateTime(new Date());
            return super.update(entity);
        }

        throw new ServiceException(String.format("Invalid model input to update, %s", entity));
    }

    private boolean isConfigExists (Integer... configIds)
    {
        for (Integer configId : configIds)
        {
            if (configRepository.get(configId) == null)
            {
                return false;
            }
        }

        return true;
    }
}
