package org.edu.energycourse.mc.biz.repository;

import java.util.List;

public interface BaseRepository<T>
{
    T get(Integer id);
    Integer insert(final T entity);
    Integer update(final T entity);
    Integer delete(Integer id);
    List<T> getAll();
}
