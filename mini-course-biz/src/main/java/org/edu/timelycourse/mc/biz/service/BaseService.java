package org.edu.timelycourse.mc.biz.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.edu.timelycourse.mc.beans.criteria.BaseCriteria;
import org.edu.timelycourse.mc.beans.model.BaseModel;
import org.edu.timelycourse.mc.biz.repository.BaseRepository;
import org.edu.timelycourse.mc.biz.utils.LocaleMessageSource;
import org.edu.timelycourse.mc.common.constants.Constants;
import org.edu.timelycourse.mc.common.exception.ServiceException;
import org.edu.timelycourse.mc.beans.paging.PagingBean;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Marco on 2018/3/31.
 */
public abstract class BaseService<T extends BaseModel>
{
    protected final BaseRepository<T> repository;

    @Autowired
    protected LocaleMessageSource messageSource;

    public BaseService (BaseRepository<T> repository)
    {
        this.repository = repository;
    }

    public T get (Integer id)
    {
        if (id != null && id > 0)
        {
            return this.repository.get(id);
        }
        throw new ServiceException(String.format("Invalid entity id (%d) for fetching", id));
    }

    @Transactional(rollbackFor = Exception.class)
    public T add (T entity)
    {
        if (entity != null)
        {
            if (entity.isValidInput())
            {
                Integer result = this.repository.insert(entity);
                if (result > 0)
                {
                    return entity;
                }
                throw new ServiceException("Failed to add entity: " + entity);
            }
            throw new ServiceException("Invalid entity input to add: " + entity);
        }
        throw new ServiceException("Empty entity to add");
    }

    @Transactional(rollbackFor = Exception.class)
    public T update (T entity)
    {
        if (entity != null && entity.isValidInput() && EntityUtils.isValidEntityId(entity.getId()))
        {
            Integer result = this.repository.update(entity);
            if (result > 0)
            {
                return entity;
            }

            throw new ServiceException("Failed to update entity: " + entity);
        }

        throw new ServiceException("Invalid entity to update: " + entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer delete (Integer id)
    {
        if (EntityUtils.isValidEntityId(id))
        {
            return this.repository.delete(id);
        }

        throw new ServiceException(String.format("Invalid entity id (%d) for deletion", id));
    }

    public List<T> getAll ()
    {
        return this.repository.getAll();
    }

    public PagingBean<T> findByPage (T entity, Integer pageNum, Integer pageSize)
    {
        try
        {
            PageHelper.startPage(
                    EntityUtils.isValidEntityId(pageNum) ? pageNum : 1,
                    EntityUtils.isValidEntityId(pageSize) ? pageSize : Constants.DEFAULT_PAGE_SIZE);

            Page<T> result = this.repository.getByPage(entity);
            return new PagingBean<T>(result);
        }
        catch (Exception ex)
        {
            throw new ServiceException(String.format(
                    "Failed to find by page - [entity: %s, pageNum: %d, pageSize: %d]",
                    entity, pageNum, pageSize), ex);
        }
    }

    public PagingBean<T> findByCriteria (BaseCriteria criteria, Integer pageNum, Integer pageSize)
    {
        try
        {
            PageHelper.startPage(
                    EntityUtils.isValidEntityId(pageNum) ? pageNum : 1,
                    EntityUtils.isValidEntityId(pageSize) ? pageSize : Constants.DEFAULT_PAGE_SIZE);

            Page<T> result = this.repository.getByCriteria(criteria);
            return new PagingBean<T>(result);
        }
        catch (Exception ex)
        {
            throw new ServiceException(String.format(
                    "Failed to find by criteria - [criteria: %s, pageNum: %d, pageSize: %d]",
                    criteria, pageNum, pageSize), ex);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public T saveOrUpdate(T entity)
    {
        return entity.getId() != null ? update(entity) : add(entity);
    }
}
