package org.edu.timelycourse.mc.biz.service;

import org.edu.timelycourse.mc.beans.model.ContractInvoiceModel;
import org.edu.timelycourse.mc.biz.repository.ContractRepository;
import org.edu.timelycourse.mc.biz.repository.InvoiceRepository;
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
public class InvoiceService extends BaseService<ContractInvoiceModel>
{
    private static Logger LOGGER = LoggerFactory.getLogger(InvoiceService.class);

    private ContractRepository contractRepository;

    @Autowired
    public InvoiceService(InvoiceRepository repository,
                          ContractRepository contractRepository)
    {
        super(repository);
        this.contractRepository = contractRepository;
    }

    @Override
    public ContractInvoiceModel add(ContractInvoiceModel entity)
    {
        if (entity.isValidInput())
        {
            // check if contract exists
            Asserts.assertEntityNotNullById(contractRepository, entity.getContractId());

            entity.setCreationTime(new Date());
            return super.add(entity);
        }

        throw new ServiceException(String.format("Invalid model data to add, %s", entity));
    }

    @Override
    public ContractInvoiceModel update(ContractInvoiceModel entity)
    {
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
