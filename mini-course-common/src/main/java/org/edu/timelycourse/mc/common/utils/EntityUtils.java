package org.edu.timelycourse.mc.common.utils;

/**
 * Created by x36zhao on 2018/4/11.
 */
public final class EntityUtils
{
    public static boolean isValidEntityId (Integer... entityIds)
    {
        if (entityIds != null && entityIds.length > 0)
        {
            for (Integer entityId : entityIds)
            {
                if (entityId == null || entityId <= 0)
                {
                    return false;
                }
            }
            return true;
        }

        return false;
    }
}
