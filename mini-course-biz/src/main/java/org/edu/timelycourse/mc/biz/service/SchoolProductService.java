package org.edu.timelycourse.mc.biz.service;

import org.edu.timelycourse.mc.biz.model.SchoolModel;
import org.edu.timelycourse.mc.biz.model.SchoolProductModel;
import org.edu.timelycourse.mc.biz.repository.SchoolProductRepository;
import org.edu.timelycourse.mc.biz.repository.SchoolRepository;
import org.edu.timelycourse.mc.biz.repository.SystemConfigRepository;
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
public class SchoolProductService extends BaseService<SchoolProductModel>
{
    private static Logger LOGGER = LoggerFactory.getLogger(SchoolProductService.class);

    private SchoolProductRepository productRepository;
    private SchoolRepository schoolRepository;
    private SystemConfigRepository systemConfigRepository;

    @Autowired
    public SchoolProductService(SchoolProductRepository repository,
                                SchoolRepository schoolRepository,
                                SystemConfigRepository systemConfigRepository)
    {
        super(repository);
        this.productRepository = repository;
        this.schoolRepository = schoolRepository;
        this.systemConfigRepository = systemConfigRepository;
    }

    @Override
    public SchoolProductModel add(SchoolProductModel entity)
    {
        if (entity.isValidInput())
        {
            // check if school entity exists
            Asserts.assertEntityNotNullById(schoolRepository, entity.getSchoolId());

            // check if product type exists
            if (entity.getParentId() == null)
            {
                Asserts.assertEntityNotNullById(systemConfigRepository, entity.getProductType());
            }

            // check if parent entity exists if given
            if (entity.getParentId() != null && EntityUtils.isValidEntityId(entity.getParentId()))
            {
                Asserts.assertEntityNotNullById(repository, entity.getParentId());

                // reset the product type as it's only applicable for root
                entity.setProductType(null);
            }

            // check if product name exists before add
            if (productRepository.getByEntity(new SchoolProductModel(
                    entity.getProductName(), entity.getParentId(), entity.getSchoolId())) != null)
            {
                throw new ServiceException(String.format(
                        "Product already exists with [name: %s, parent: %d, school: %d]",
                        entity.getProductName(), entity.getParentId(), entity.getSchoolId()));
            }

            entity.setCreationTime(new Date());
            return super.add(entity);
        }

        throw new ServiceException(String.format(
                "Invalid model input to add, %s", entity));
    }

    @Override
    public SchoolProductModel update(SchoolProductModel entity)
    {
        entity.setLastUpdateTime(new Date());
        return super.update(entity);
    }
}
