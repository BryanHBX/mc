package org.edu.timelycourse.mc.biz.service;

import org.edu.timelycourse.mc.biz.entity.BaseEntity;
import org.edu.timelycourse.mc.biz.repository.BaseRepository;
import org.edu.timelycourse.mc.common.exception.ServiceException;

import java.util.List;

/**
 * Created by Marco on 2018/3/31.
 */
public abstract class BaseService<T extends BaseEntity>
{
    private final BaseRepository<T> repository;
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

    public T add (T entity)
    {
        if (entity != null)
        {
            Integer result = this.repository.insert(entity);
            if (result > 0)
            {
                return entity;
            }
            throw new ServiceException("Failed to add entity: " + entity);
        }

        throw new ServiceException("Empty entity to add");
    }

    public T update (T entity)
    {
        if (entity != null && entity.getId() != null)
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

    public Integer delete (Integer id)
    {
        if (id != null && id > 0)
        {
            return this.repository.delete(id);
        }

        throw new ServiceException(String.format("Invalid entity id (%d) for deletion", id));
    }

    public List<T> getAll ()
    {
        return this.repository.getAll();
    }

    public T saveOrUpdate(T entity)
    {
        return entity.getId() != null ? update(entity) : add(entity);
    }

    //protected abstract String getServiceCategory();
    //protected abstract String getServiceDomain();
}
