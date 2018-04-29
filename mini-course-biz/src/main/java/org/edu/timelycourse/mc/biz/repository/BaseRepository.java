package org.edu.timelycourse.mc.biz.repository;

import com.github.pagehelper.Page;
import org.edu.timelycourse.mc.beans.criteria.BaseCriteria;
import org.edu.timelycourse.mc.beans.model.BaseModel;

import java.util.List;

public interface BaseRepository<T extends BaseModel>
{
    T get(Integer id);
    Integer insert(final T entity);
    Integer update(final T entity);
    Integer delete(Integer id);
    List<T> getAll();
    List<T> getListByCriteria (final BaseCriteria criteria);
    T getByEntity (final T entity);
    Page<T> getByPage (final T entity);
    Page<T> getByCriteria (final BaseCriteria criteria);
}
