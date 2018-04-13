package org.edu.timelycourse.mc.biz.service;

import org.edu.timelycourse.mc.biz.model.ContractModel;
import org.edu.timelycourse.mc.biz.model.InvoiceModel;
import org.edu.timelycourse.mc.biz.model.StudentModel;
import org.edu.timelycourse.mc.biz.model.UserModel;
import org.edu.timelycourse.mc.biz.repository.*;
import org.edu.timelycourse.mc.biz.utils.Asserts;
import org.edu.timelycourse.mc.common.exception.ServiceException;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by x36zhao on 2017/3/17.
 */
@Service
public class ContractService extends BaseService<ContractModel>
{
    private static Logger LOGGER = LoggerFactory.getLogger(ContractService.class);

    private StudentRepository studentRepository;
    private InvoiceRepository invoiceRepository;
    private SchoolRepository schoolRepository;
    private UserRepository userRepository;
    private ContractRepository contractRepository;

    @Autowired
    public ContractService(ContractRepository repository,
                           StudentRepository studentRepository,
                           InvoiceRepository invoiceRepository,
                           SchoolRepository schoolRepository,
                           UserRepository userRepository)
    {
        super(repository);
        this.studentRepository = studentRepository;
        this.invoiceRepository = invoiceRepository;
        this.schoolRepository = schoolRepository;
        this.userRepository = userRepository;
        this.contractRepository = repository;
    }

    private StudentModel initStudentModelBeforeAdd (final ContractModel model)
    {
        StudentModel studentEntity = model.getStudent();
        if (studentEntity != null)
        {
            studentEntity.setConsultantId(model.getConsultantId());
            studentEntity.setCourseId(model.getCourseId());
            studentEntity.setLevelId(model.getLevelId());
            studentEntity.setSubCourseId(model.getSubCourseId());
            studentEntity.setSubLevelId(model.getSubLevelId());
            return studentEntity;
        }

        throw new ServiceException("Student model is not given in contract");
    }

    @Override
    public ContractModel add(ContractModel model)
    {
        if (model.isValidInput())
        {
            StudentModel studentEntity = initStudentModelBeforeAdd(model);

            // check if user exists
            UserModel consultant = (UserModel) Asserts.assertEntityNotNullById(userRepository, model.getConsultantId());

            // check if student exists in case selection from suggest lookup
            if (EntityUtils.isValidEntityId(model.getStudentId()))
            {
                studentEntity = (StudentModel) Asserts.assertEntityNotNullById(studentRepository, model.getStudentId());
            }
            else
            {
                // save student
                studentRepository.insert(model.getStudent());
                studentEntity = model.getStudent();
            }

            // check if same school id in place
            if (consultant.getSchoolId() != model.getSchoolId() &&
                    studentEntity.getSchoolId() != model.getSchoolId())
            {
                throw new ServiceException(String.format(
                        "Conflict school id in payload request - [consultant (sid: %d), student (sid: %d), contract (sid: %d)]",
                        consultant.getSchoolId(), model.getSchoolId(), studentEntity.getSchoolId()
                ));
            }

            // check if contract no. exists already or not
            if (contractRepository.getByEntity(new ContractModel(
                    model.getContractNo(), model.getSchoolId())) != null)
            {
                throw new ServiceException(String.format(
                        "Contract already exists with [no: %s, schoolId: %d]",
                        model.getContractNo(), model.getSchoolId()));
            }

            model.setStudentId(studentEntity.getId());
            model.setCreationTime(new Date());

            // save contract
            repository.insert(model);

            // save invoices
            if (model.getInvoices() != null)
            {
                for (InvoiceModel invoice : model.getInvoices())
                {
                    invoice.setCreationTime(new Date());
                    invoice.setContractId(model.getId());
                    invoiceRepository.insert(invoice);
                }
            }

            return model;
        }

        throw new ServiceException(String.format("Invalid model data to add, %s", model));
    }

    @Override
    public Integer delete (Integer id)
    {
        return repository.delete(id);
    }

    @Override
    public ContractModel update(ContractModel entity)
    {
        if (entity.isValidInput() && EntityUtils.isValidEntityId(entity.getId()))
        {
            // check if entity exists
            ContractModel entityInDb = (ContractModel) Asserts.assertEntityNotNullById(repository, entity.getId());

            // check if school changed
            if (entity.getSchoolId() != null && !entity.getSchoolId().equals(entityInDb.getSchoolId()))
            {
                throw new ServiceException(String.format(
                        "It's not allowed to change the school from %d to %d",
                        entityInDb.getSchoolId(), entity.getSchoolId()));
            }
            entity.setSchoolId(entityInDb.getSchoolId());

            // check if contract no exists
            entityInDb = contractRepository.getByEntity(new ContractModel(entity.getContractNo(), entity.getSchoolId()));
            if (entityInDb != null && !entityInDb.getId().equals(entity.getId()))
            {
                throw new ServiceException(String.format(
                        "Contract already exists with [no: %s, schoolId: %d]",
                        entity.getContractNo(), entity.getSchoolId()));
            }

            entity.setLastUpdateTime(new Date());
            return super.update(entity);
        }

        throw new ServiceException(String.format("Invalid model data to update, %s", entity));
    }

}
