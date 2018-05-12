package org.edu.timelycourse.mc.biz.service;

import org.edu.timelycourse.mc.beans.dto.ContractRefundDTO;
import org.edu.timelycourse.mc.beans.dto.ContractTransformDTO;
import org.edu.timelycourse.mc.beans.enums.*;
import org.edu.timelycourse.mc.beans.model.ContractModel;
import org.edu.timelycourse.mc.beans.model.ContractInvoiceModel;
import org.edu.timelycourse.mc.beans.model.StudentModel;
import org.edu.timelycourse.mc.beans.model.UserModel;
import org.edu.timelycourse.mc.beans.paging.PagingBean;
import org.edu.timelycourse.mc.biz.repository.*;
import org.edu.timelycourse.mc.biz.utils.Asserts;
import org.edu.timelycourse.mc.biz.utils.SecurityContextHelper;
import org.edu.timelycourse.mc.common.exception.ServiceException;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.edu.timelycourse.mc.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by x36zhao on 2017/3/17.
 */
@Service
public class ContractService extends BaseService<ContractModel>
{
    private static Logger LOGGER = LoggerFactory.getLogger(ContractService.class);

    private StudentRepository studentRepository;
    private ContractInvoiceRepository invoiceRepository;
    private SchoolRepository schoolRepository;
    private UserRepository userRepository;
    private ContractRepository contractRepository;

    @Autowired
    public ContractService(ContractRepository repository,
                           StudentRepository studentRepository,
                           ContractInvoiceRepository invoiceRepository,
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

    private void initStudentModelBeforeAdd (ContractModel model)
    {
        StudentModel studentEntity = model.getStudent();
        if (studentEntity != null)
        {
            studentEntity.setConsultantId(model.getConsultantId());
            studentEntity.setCourseId(model.getCourseId());
            studentEntity.setLevelId(model.getLevelId());
            studentEntity.setSubCourseId(model.getSubCourseId());
            studentEntity.setSubLevelId(model.getSubLevelId());
            studentEntity.setSchoolId(SecurityContextHelper.getSchoolIdFromPrincipal());
            return;
        }

        throw new ServiceException("Student model is not given in contract");
    }

    private void initContract (ContractModel model)
    {
        model.setSchoolId(SecurityContextHelper.getSchoolIdFromPrincipal());
        model.setPayStatus(model.getPaid() < model.getTotalPrice() ?
                EContractDebtStatus.ARREARAGE.code() : EContractDebtStatus.DONE.code());

        initStudentModelBeforeAdd(model);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ContractModel add(ContractModel model)
    {
        initContract(model);
        model.setContractStatus(EContractStatus.ONGOING.code());

        if (model.isValidInput())
        {
            StudentModel studentEntity =  model.getStudent();

            // check if user exists
            UserModel consultant = (UserModel) Asserts.assertEntityNotNullById(userRepository, model.getConsultantId());

            // check if student exists in case selection from suggest lookup
            if (model.getStudentId() != null && EntityUtils.isValidEntityId(model.getStudentId()))
            {
                studentEntity = (StudentModel) Asserts.assertEntityNotNullById(studentRepository, model.getStudentId());
                BeanUtils.copyProperties(model.getStudent(), studentEntity, "id");
                studentRepository.update(studentEntity);
            }
            else
            {
                // save student
                studentRepository.insert(model.getStudent());
            }

            // check if same school id in place
            if (!consultant.getSchoolId().equals(model.getSchoolId()) &&
                    !studentEntity.getSchoolId().equals(model.getSchoolId()))
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

            model.setRemainedPeriod(model.getEnrollPeriod());
            model.setStudentId(studentEntity.getId());
            model.setCreationTime(new Date());
            model.setPaid(model.getInvoicePayTotal());
            model.setArrangeStatus(EContractArrangementStatus.TODO.code());

            // save contract
            repository.insert(model);

            // save invoices
            if (model.getInvoices() != null)
            {
                for (ContractInvoiceModel invoice : model.getInvoices())
                {
                    if (invoice.getPrice() > 0 && StringUtils.isNotEmpty(invoice.getInvoiceNo()))
                    {
                        invoice.setSchoolId(model.getSchoolId());
                        invoice.setCreationTime(new Date());
                        invoice.setContractId(model.getId());
                        invoice.setOwnerId(model.getConsultantId());
                        invoice.setStatus(!model.getEnrollType().equals(EEnrollmentType.TRANSFER.code())
                                ? model.getEnrollType() : EEnrollmentType.FRESHER.code());
                        invoiceRepository.insert(invoice);
                    }
                }
            }

            return model;
        }

        throw new ServiceException(String.format("Invalid model data to add, %s", model));
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean refund (ContractRefundDTO dto)
    {
        if (dto.isValid())
        {
            // check if contract exists
            ContractModel contract = (ContractModel) Asserts.assertEntityNotNullById(repository, dto.getContractId());

            // check any hacks
            if (!contract.getSchoolId().equals(dto.getSchoolId()))
            {
                throw new ServiceException("You don't have permission update the " +
                        "contract data which is not owned by you");
            }

//            // check if money paid out already
//            if (contract.getPayStatus().equals(EContractDebtStatus.ARREARAGE.code()))
//            {
//                throw new ServiceException("It's not allowed to refund when it's still under arrearage state");
//            }

            // validate the refund price and period
            if (dto.getRefundPrice() > contract.getRemainedPrice() ||
                    dto.getRefundPeriod() > contract.getRemainedPeriod() ||
                    dto.getRefundOtherPrice() > contract.getOtherPrice())
            {
                throw new ServiceException("Refund price or period is beyond of the original contract value");
            }

            // add invoice with refund status
            ContractInvoiceModel invoice = new ContractInvoiceModel();
            invoice.setContractId(contract.getId());
            invoice.setSchoolId(contract.getSchoolId());
            invoice.setPrice(-(contract.getRemainedPrice() + dto.getRefundOtherPrice()));
            invoice.setCreationTime(dto.getRefundDate());
            invoice.setStatus(EInvoiceStatus.REFUND.code());
            invoiceRepository.insert(invoice);

            // update contract
            contract.setContractStatus(EContractStatus.FINISHED.code());
            contract.setRefundPrice(contract.getRefundPrice() + contract.getRemainedPrice());
            contract.setPayStatus(EContractDebtStatus.DONE.code());
            contract.setRemainedPeriod(0);
            contract.setLastUpdateTime(new Date());
            repository.update(contract);

            return true;
        }

        throw new ServiceException(String.format("Invalid DTO for contract refund: %s", dto));
    }

    @Transactional(rollbackFor = Exception.class)
    public ContractModel transform (ContractTransformDTO dto)
    {
        if (dto.isValid())
        {
            // check if contract exists
            ContractModel source = (ContractModel) Asserts.assertEntityNotNullById(repository, dto.getContractId());

            // check any hacks
            if (!source.getSchoolId().equals(dto.getSchoolId()))
            {
                throw new ServiceException("You don't have permission update the " +
                        "contract data which is not owned by you");
            }

            // calculate the period from source contract
            double transferPeriod = dto.getTransformPrice() / source.getPricePerPeriod();
            double remainedPeriod = source.getRemainedPeriod() - transferPeriod;
            if (remainedPeriod < 0)
            {
                throw new ServiceException(String.format("Invalid period to be transferred (%s) " +
                        "as remained period from source contract (%s) is negative (%d) after transform",
                        dto, source, remainedPeriod));
            }

            // add new contract after transform
            ContractModel target = new ContractModel();
            BeanUtils.copyProperties(source, target,
                    "id", "consultant", "supervisor", "student", "contractDate",
                    "level", "subLevel", "course", "subCourse", "otherPrice", "remainedPeriod");
            target.setCourseId(dto.getTargetCourse());
            target.setSubCourseId(dto.getTargetSubCourse());
            target.setEnrollPeriod(dto.getTransformPeriod());
            target.setContractPrice(dto.getTransformPrice());
            target.setTotalPrice(dto.getTransformPrice());
            target.setEnrollType(EEnrollmentType.TRANSFER.code());
            target.setRemainedPeriod(target.getEnrollPeriod());
            target.setPayStatus(EContractDebtStatus.DONE.code());
            target.setPaid(target.getTotalPrice());
            target.setContractDate(dto.getTransformDate());
            repository.insert(target);

            // update the original contract
            source.setTransferPeriod(source.getTransferPeriod() + transferPeriod);
            source.setRemainedPeriod(remainedPeriod);
            source.setLastUpdateTime(new Date());
            source.setRefundPrice(target.getTotalPrice());
            if (source.getRemainedPeriod() == 0)
            {
                source.setContractStatus(EContractStatus.FINISHED.code());
                source.setPayStatus(EContractDebtStatus.DONE.code());
            }
            repository.update(source);

            return target;
        }

        throw new ServiceException(String.format("Invalid DTO for contract transform: %s", dto));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer delete (Integer id)
    {
        if (EntityUtils.isValidEntityId(id))
        {
            // check if entity exists
            ContractModel contract = (ContractModel) Asserts.assertEntityNotNullById(repository, id);

            // validate permission
            SecurityContextHelper.validatePermission(contract.getSchoolId(), null);

            // delete invoices if have
            List<ContractInvoiceModel> invoices = invoiceRepository.getByContractId(id, contract.getSchoolId());
            if (invoices != null)
            {
                for (ContractInvoiceModel invoice : invoices)
                {
                    invoiceRepository.delete(invoice.getId());
                }
            }

            return repository.delete(id);
        }

        throw new ServiceException(String.format("Invalid entity id (%d) to delete", id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ContractModel update(ContractModel entity)
    {
        if (entity.isValidInput() && EntityUtils.isValidEntityId(entity.getId()))
        {
            // check if entity exists
            ContractModel entityInDb = (ContractModel) Asserts.assertEntityNotNullById(repository, entity.getId());

            // validate permission
            SecurityContextHelper.validatePermission(entityInDb.getSchoolId(), null);

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

    @Override
    public PagingBean<ContractModel> findByPage(ContractModel entity, Integer pageNum, Integer pageSize)
    {
        entity.setSchoolId(SecurityContextHelper.getSchoolIdFromPrincipal());
        return super.findByPage(entity, pageNum, pageSize);
    }
}
