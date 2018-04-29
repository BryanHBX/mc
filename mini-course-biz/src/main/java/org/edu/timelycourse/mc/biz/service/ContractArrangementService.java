package org.edu.timelycourse.mc.biz.service;

import org.edu.timelycourse.mc.beans.criteria.ContractArrangementCriteria;
import org.edu.timelycourse.mc.beans.enums.EContractArrangementStatus;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ContractArrangementModel add(ContractArrangementModel model)
    {
        model.setSchoolId(SecurityContextHelper.getSchoolIdFromPrincipal());
        if (model.isValidInput())
        {
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

            // check teacher
            Asserts.assertEntityNotNullById(userRepository, model.getTeacherId(), String.format(
                    "Teacher with id (%d) does not exist", model.getTeacherId()));

            // add clazz if given
            if (model.getClazz() != null)
            {
                clazzService.add(model.getClazz());
                model.setClassId(model.getClazz().getId());
            }
            repository.insert(model);

            // update contract arrange status
            contract.setArrangeStatus(EContractArrangementStatus.PLANNED.code());
            contractRepository.update(contract);

            return model;
        }

        throw new ServiceException("Invalid arrangment input to add: " + model);
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
