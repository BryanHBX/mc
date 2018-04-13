package org.edu.timelycourse.mc.biz.service;

import org.edu.timelycourse.mc.biz.model.InvoiceModel;
import org.edu.timelycourse.mc.biz.repository.InvoiceRepository;
import org.edu.timelycourse.mc.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by x36zhao on 2017/3/17.
 */
@Service
public class InvoiceService extends BaseService<InvoiceModel>
{
    private static Logger LOGGER = LoggerFactory.getLogger(InvoiceService.class);

    @Autowired
    public InvoiceService(InvoiceRepository repository)
    {
        super(repository);
    }

    @Override
    public InvoiceModel add(InvoiceModel entity)
    {
        if (entity.isValidInput())
        {
            entity.setCreationTime(new Date());
            return super.add(entity);
        }

        throw new ServiceException(String.format("Invalid model data to add, %s", entity));
    }

    @Override
    public InvoiceModel update(InvoiceModel entity)
    {
        if (entity.isValidInput())
        {
            entity.setLastUpdateTime(new Date());
            return super.update(entity);
        }

        throw new ServiceException(String.format("Invalid model data to add, %s", entity));
    }

}
