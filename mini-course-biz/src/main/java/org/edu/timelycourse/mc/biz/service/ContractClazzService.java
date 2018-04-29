package org.edu.timelycourse.mc.biz.service;

import com.github.pagehelper.Page;
import org.edu.timelycourse.mc.beans.criteria.ContractClazzCriteria;
import org.edu.timelycourse.mc.beans.enums.EClazzStatus;
import org.edu.timelycourse.mc.beans.model.ContractClazzModel;
import org.edu.timelycourse.mc.biz.repository.ContractClazzRepository;
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

import java.util.Date;

/**
 * Created by x36zhao on 2017/3/17.
 */
@Service
public class ContractClazzService extends BaseService<ContractClazzModel>
{
    private static Logger LOGGER = LoggerFactory.getLogger(ContractClazzService.class);

    private UserRepository userRepository;

    @Autowired
    public ContractClazzService(ContractClazzRepository repository,
                                UserRepository userRepository)
    {
        super(repository);
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ContractClazzModel add(ContractClazzModel entity)
    {
        entity.setSchoolId(SecurityContextHelper.getSchoolIdFromPrincipal());
        if (entity.isValidInput())
        {
            // check teacher
            Asserts.assertEntityNotNullById(userRepository, entity.getTeacherId(), String.format(
                    "Teacher with id (%d) does not exist", entity.getTeacherId()));

            // check name
            if (isClazzNameExisted(entity))
            {
                throw new ServiceException(String.format(
                        "The clazz (name: %s) already exists under teacher (id: %d)",
                        entity.getName(), entity.getTeacherId()));
            }

            entity.setStatus(EClazzStatus.NORMAL.code());
            entity.setCreationTime(new Date());
            repository.insert(entity);
            return entity;
        }
        throw new ServiceException("Invalid entity input to add: " + entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer delete(Integer id)
    {
        if (EntityUtils.isValidEntityId(id))
        {
            ContractClazzModel clazz = (ContractClazzModel) Asserts.assertEntityNotNullById(repository, id);
            if (!clazz.getSchoolId().equals(SecurityContextHelper.getSchoolIdFromPrincipal()))
            {
                throw new ServiceException("Un-authorized to access the resouce");
            }
            return repository.delete(id);
        }
        throw new ServiceException(String.format("Illegal clazz id (%d) for deletion", id));
    }

    private boolean isClazzNameExisted (ContractClazzModel entity)
    {
        ContractClazzCriteria criteria = new ContractClazzCriteria(entity.getName(), entity.getTeacherId(), entity.getSchoolId());
        Page<ContractClazzModel> result = repository.getByCriteria(criteria);
        return result.getTotal() > 0;
    }
}
