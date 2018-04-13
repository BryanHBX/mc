package org.edu.timelycourse.mc.biz.service;

import com.sun.tools.javac.util.Assert;
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

import javax.swing.text.html.parser.Entity;
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

        throw new ServiceException(String.format("Invalid model input to add, %s", entity));
    }

    @Override
    public Integer delete (Integer id)
    {
        if (EntityUtils.isValidEntityId(id))
        {
            SchoolProductModel entity = (SchoolProductModel) Asserts.assertEntityNotNullById(repository, id);

            // remove all children
            if (entity.getChildren() != null)
            {
                for (SchoolProductModel child : entity.getChildren())
                {
                    delete(child.getId());
                }

                /*
                for (int i = entity.getChildren().size() - 1; i >= 0; i--)
                {
                    repository.delete(entity.getChildren().get(i).getId());
                }
                */
            }

            return repository.delete(id);
        }

        throw new ServiceException(String.format("Invalid entity id (%d) for deletion", id));
    }


    @Override
    public SchoolProductModel update(SchoolProductModel entity)
    {
        if (entity.isValidInput() && EntityUtils.isValidEntityId(entity.getId()))
        {
            // check if entity exists with given id
            SchoolProductModel entityInDb = (SchoolProductModel) Asserts.assertEntityNotNullById(
                    repository, entity.getId());

            // replace using the school id from entityInDb
            // cause it's not allowed overwritten over payload during update
            entity.setSchoolId(entity.getSchoolId());

            // check if any change with respect to its parent
            if (entity.getParentId() != entityInDb.getParentId())
            {
                throw new ServiceException(String.format(
                        "It's not allowed to change the parent from %d to %d",
                        entityInDb.getParentId(), entity.getParentId()));
            }

            // check if product type exists
            if (entity.getParentId() == null)
            {
                Asserts.assertEntityNotNullById(systemConfigRepository, entity.getProductType());
            }
            else
            {
                entity.setProductType(null);
            }

            // check if product name exists before add
            entityInDb = productRepository.getByEntity(new SchoolProductModel(
                    entity.getProductName(), entity.getParentId(), entity.getSchoolId()
            ));

            if (entityInDb != null && !entityInDb.getId().equals(entity.getId()))
            {
                throw new ServiceException(String.format(
                        "Product already exists with [name: %s, parent: %d, school: %d]",
                        entity.getProductName(), entity.getParentId(), entity.getSchoolId()));
            }

            entity.setLastUpdateTime(new Date());
            return super.update(entity);
        }

        throw new ServiceException(String.format("Invalid model input to update, %s", entity));
    }
}
