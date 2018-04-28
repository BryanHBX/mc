package org.edu.timelycourse.mc.biz.service;

import org.edu.timelycourse.mc.beans.criteria.BaseCriteria;
import org.edu.timelycourse.mc.beans.criteria.InvoiceCriteria;
import org.edu.timelycourse.mc.beans.enums.EContractDebtStatus;
import org.edu.timelycourse.mc.beans.model.ContractInvoiceModel;
import org.edu.timelycourse.mc.beans.model.ContractModel;
import org.edu.timelycourse.mc.beans.paging.PagingBean;
import org.edu.timelycourse.mc.biz.repository.ContractRepository;
import org.edu.timelycourse.mc.biz.repository.ContractInvoiceRepository;
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
import java.util.List;

/**
 * Created by x36zhao on 2017/3/17.
 */
@Service
public class InvoiceService extends BaseService<ContractInvoiceModel>
{
    private static Logger LOGGER = LoggerFactory.getLogger(InvoiceService.class);

    private ContractRepository contractRepository;
    private ContractInvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(ContractInvoiceRepository repository,
                          ContractRepository contractRepository)
    {
        super(repository);
        this.invoiceRepository = repository;
        this.contractRepository = contractRepository;
    }

    @Override
    @Transactional
    public ContractInvoiceModel add(ContractInvoiceModel entity)
    {
        entity.setSchoolId(SecurityContextHelper.getSchoolIdFromPrincipal());
        if (entity.isValidInput())
        {
            // check if contract exists
            ContractModel contractEntity = (ContractModel) Asserts.assertEntityNotNullById(contractRepository, entity.getContractId());
            if (!contractEntity.getSchoolId().equals(entity.getSchoolId()))
            {
                throw new ServiceException("You don't have permission update the " +
                        "contract data which is not owned by you");
            }

            entity.setCreationTime(new Date());
            repository.insert(entity);

            // update contract pay status
            contractEntity.setPayStatus(contractEntity.getPaid() + entity.getPrice() >= contractEntity.getTotalPrice() ?
                    EContractDebtStatus.DONE.code() : EContractDebtStatus.ARREARAGE.code());
            contractEntity.setPaid(contractEntity.getPaid() + entity.getPrice());

            contractRepository.update(contractEntity);

            return entity;
        }

        throw new ServiceException(String.format("Invalid model data to add, %s", entity));
    }

    @Transactional
    public List<ContractInvoiceModel> add (List<ContractInvoiceModel> entities)
    {
        if (entities != null)
        {
            for (ContractInvoiceModel entity : entities)
            {
                add(entity);
            }
        }

        return entities;
    }

    @Override
    public PagingBean<ContractInvoiceModel> findByCriteria(BaseCriteria criteria, Integer pageNum, Integer pageSize)
    {
        PagingBean<ContractInvoiceModel> invoices = super.findByCriteria(criteria, pageNum, pageSize);
        if (invoices.getItems() != null)
        {
            for (ContractInvoiceModel invoice : invoices.getItems())
            {
                invoice.setContract(contractRepository.get(invoice.getContractId()));
            }
        }
        return invoices;
    }

    public Double getTotalIncomeByCriteria (InvoiceCriteria criteria)
    {
        criteria.setPositiveFlag(1);
        return invoiceRepository.getTotalFeeByCriteria(criteria);
    }

    public Double getTotalRefundByCriteria (InvoiceCriteria criteria)
    {
        criteria.setPositiveFlag(0);
        return invoiceRepository.getTotalFeeByCriteria(criteria);
    }

    @Override
    @Transactional
    public ContractInvoiceModel update(ContractInvoiceModel entity)
    {
        entity.setSchoolId(SecurityContextHelper.getSchoolIdFromPrincipal());
        if (entity.isValidInput() && EntityUtils.isValidEntityId(entity.getId()))
        {
            // check if entity exists
            ContractInvoiceModel entityInDb = (ContractInvoiceModel) Asserts.assertEntityNotNullById(repository, entity.getId());

            // check if any change with respect to contract
            if (entity.getContractId() != entityInDb.getContractId())
            {
                throw new ServiceException(String.format(
                        "It's not allowed to change the contract from %d to %d",
                        entityInDb.getContractId(), entity.getContractId()));
            }

            // use the contract id in entityInDb
            entity.setContractId(entityInDb.getContractId());
            entity.setLastUpdateTime(new Date());
            return super.update(entity);
        }

        throw new ServiceException(String.format("Invalid model data to add, %s", entity));
    }

}
