package org.edu.timelycourse.mc.biz.repository;

import org.edu.timelycourse.mc.biz.entity.BaseEntity;

import java.util.List;

public interface BaseRepository<T extends BaseEntity>
{
    T get(Integer id);
    Integer insert(final T entity);
    Integer update(final T entity);
    Integer delete(Integer id);
    List<T> getAll();
}
