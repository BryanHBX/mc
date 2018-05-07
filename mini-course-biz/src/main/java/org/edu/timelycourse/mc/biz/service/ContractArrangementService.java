package org.edu.timelycourse.mc.biz.service;

import org.edu.timelycourse.mc.beans.criteria.ContractArrangementCriteria;
import org.edu.timelycourse.mc.beans.dto.ContractArrangementDTO;
import org.edu.timelycourse.mc.beans.enums.EContractArrangementStatus;
import org.edu.timelycourse.mc.beans.enums.EContractStatus;
import org.edu.timelycourse.mc.beans.model.BaseModel;
import org.edu.timelycourse.mc.beans.model.ContractArrangementModel;
import org.edu.timelycourse.mc.beans.model.ContractModel;
import org.edu.timelycourse.mc.biz.repository.ContractArrangementRepository;
import org.edu.timelycourse.mc.biz.repository.ContractClazzRepository;
import org.edu.timelycourse.mc.biz.repository.ContractRepository;
import org.edu.timelycourse.mc.biz.repository.UserRepository;
import org.edu.timelycourse.mc.biz.utils.Asserts;
import org.edu.timelycourse.mc.biz.utils.SecurityContextHelper;
import org.edu.timelycourse.mc.common.exception.ServiceException;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by x36zhao on 2017/3/17.
 */
@Service
public class ContractArrangementService extends BaseService<ContractArrangementModel>
{
    private static Logger LOGGER = LoggerFactory.getLogger(ContractArrangementService.class);

    private ContractRepository contractRepository;
    private ContractClazzService clazzService;
    private UserRepository userRepository;

    @Autowired
    public ContractArrangementService (ContractArrangementRepository repository,
                                       ContractRepository contractRepository,
                                       ContractClazzService clazzService,
                                       UserRepository userRepository)
    {
        super(repository);
        this.contractRepository = contractRepository;
        this.userRepository = userRepository;
        this.clazzService = clazzService;
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer add (ContractArrangementDTO dto, Integer contractId)
    {
        dto.setContractId(contractId);
        if (dto.isValid())
        {
            if (dto.getOperationType().equalsIgnoreCase(ContractArrangementDTO.TYPE_TEACHER))
            {
                add (ContractArrangementModel.from(dto));
                return 1;
            }
            else
            {
                ContractModel contract = (ContractModel) Asserts.assertEntityNotNullById(contractRepository, contractId);
                contract.setSupervisorId(dto.getTeacher().getId());
                return contractRepository.update(contract);
            }
        }

        throw new ServiceException("Invalid arrangement input to add: " + dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ContractArrangementModel add(ContractArrangementModel model)
    {
        model.setSchoolId(SecurityContextHelper.getSchoolIdFromPrincipal());
        if (model.isValidInput())
        {
            // check contract
            ContractModel contract = (ContractModel) Asserts.assertEntityNotNullById(
                    contractRepository, model.getContractId(), String.format(
                            "Contract with id (%d) does not exist", model.getContractId()));

            // check school
            if (!contract.getSchoolId().equals(SecurityContextHelper.getSchoolIdFromPrincipal()))
            {
                throw new ServiceException(String.format("Un-authorized to access the resource"));
            }

            // check contract
            if (!model.getContractId().equals(contract.getId()))
            {
                throw new ServiceException(String.format(
                        "Unexpected contract id (%d) during arrangement deletion, which should be %d",
                        model.getContractId(), contract.getId()));
            }

            // check contract status
            if (contract.getContractStatus().equals(EContractStatus.FINISHED.code()))
            {
                throw new ServiceException("Arrangement is not allowed for the contract which is finished");
            }

            // check teacher
            Asserts.assertEntityNotNullById(userRepository, model.getTeacherId(), String.format(
                    "Teacher with id (%d) does not exist", model.getTeacherId()));

            // check if arrangement exists
            ContractArrangementModel criteria = new ContractArrangementModel();
            criteria.setClassId(model.getClazz() != null ? model.getClazz().getId() : null);
            criteria.setTeacherId(model.getTeacherId());
            criteria.setContractId(model.getContractId());

            // check arrangement
            if (repository.getByEntity(criteria) != null)
            {
                throw new ServiceException("The arrangement already exists: " + criteria);
            }

            // add clazz if given
            if (model.getClazz() != null)
            {
                clazzService.add(model.getClazz());
                model.setClassId(model.getClazz().getId());
            }

            repository.insert(model);

            // update contract arrange status
            if (!contract.getArrangeStatus().equals(EContractArrangementStatus.PLANNED.code()))
            {
                contract.setArrangeStatus(EContractArrangementStatus.PLANNED.code());
                contractRepository.update(contract);
            }

            return model;
        }

        throw new ServiceException("Invalid arrangement input to add: " + model);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer delete (Integer contractId, Integer id)
    {
        if (EntityUtils.isValidEntityId(contractId, id))
        {
            ContractModel contract = (ContractModel) Asserts.assertEntityNotNullById(contractRepository, contractId);
            ContractArrangementModel arrangement = (ContractArrangementModel) Asserts.assertEntityNotNullById(repository, id);

            // check contract
            if (!arrangement.getContractId().equals(contract.getId()))
            {
                throw new ServiceException(String.format(
                        "Unexpected contract id (%d) during arrangement deletion, which should be %d",
                        arrangement.getContractId(), contractId));
            }

            // check school
            if (!arrangement.getSchoolId().equals(SecurityContextHelper.getSchoolIdFromPrincipal()))
            {
                throw new ServiceException(String.format("Un-authorized to access the resource"));
            }

            return repository.delete(id);
        }

        throw new ServiceException(String.format("Illegal argument when delete arrangement - (contractId: %d, id: %d)", contractId, id));
    }

    public List<ContractArrangementModel> findByContract (Integer contractId)
    {
        Asserts.assertEntityNotNullById(contractRepository, contractId,
                String.format("The contract with id (%d) does not exist", contractId));

        ContractArrangementCriteria criteria = new ContractArrangementCriteria();
        criteria.setSchoolId(SecurityContextHelper.getSchoolIdFromPrincipal());
        criteria.setContractId(contractId);
        return repository.getListByCriteria(criteria);
    }
}
