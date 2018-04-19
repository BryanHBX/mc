package org.edu.timelycourse.mc.biz.utils;

import org.edu.timelycourse.mc.biz.model.BaseModel;
import org.edu.timelycourse.mc.biz.repository.BaseRepository;
import org.edu.timelycourse.mc.biz.service.BaseService;
import org.edu.timelycourse.mc.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class Asserts
{
    private static Logger LOGGER = LoggerFactory.getLogger(Asserts.class);

    public static BaseModel assertEntityNotNullById (BaseService service, Integer entityId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format(
                    "Enter assertEntityNotNullById from %s - [entityId: %d]", service.getClass().getName(), entityId));

        return assertEntityNotNull(service.get(entityId));
    }

    public static BaseModel assertEntityNotNullById (BaseRepository repository, Integer entityId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format(
                    "Enter assertEntityNotNullById from %s - [entityId: %d]", repository.getClass().getName(), entityId));

        return assertEntityNotNull(repository.get(entityId));
    }

    public static void assertListNotNull (List entities)
    {
        if (entities != null && entities.size() > 0)
        {
            return;
        }

        throw new RuntimeException("Empty list of entities");
    }

    private static BaseModel assertEntityNotNull (BaseModel entity)
    {
        if (entity != null)
        {
            return entity;
        }

        throw new ServiceException(String.format("Entity does not exist"));
    }
}
