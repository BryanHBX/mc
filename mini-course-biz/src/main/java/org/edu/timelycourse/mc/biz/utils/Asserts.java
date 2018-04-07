package org.edu.timelycourse.mc.biz.utils;

import org.edu.timelycourse.mc.biz.entity.BaseEntity;
import org.edu.timelycourse.mc.biz.repository.BaseRepository;
import org.edu.timelycourse.mc.biz.service.BaseService;
import org.edu.timelycourse.mc.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Asserts
{
    private static Logger LOGGER = LoggerFactory.getLogger(Asserts.class);

    public static BaseEntity assertEntityNotNullById (BaseService service, Integer entityId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format(
                    "Enter assertEntityNotNullById from %s - [entityId: %d]", service.getClass().getName(), entityId));

        return assertEntityNotNull(service.get(entityId));
    }

    public static BaseEntity assertEntityNotNullById (BaseRepository repository, Integer entityId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format(
                    "Enter assertEntityNotNullById from %s - [entityId: %d]", repository.getClass().getName(), entityId));

        return assertEntityNotNull(repository.get(entityId));
    }

    private static BaseEntity assertEntityNotNull (BaseEntity entity)
    {
        if (entity != null)
        {
            return entity;
        }

        throw new ServiceException(String.format("Entity does not exist"));
    }
}
