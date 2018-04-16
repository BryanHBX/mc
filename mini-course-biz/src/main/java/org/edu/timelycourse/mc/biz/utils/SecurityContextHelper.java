package org.edu.timelycourse.mc.biz.utils;

import org.edu.timelycourse.mc.biz.enums.EAuthorityName;
import org.edu.timelycourse.mc.biz.security.JwtUser;
import org.edu.timelycourse.mc.common.exception.ServiceException;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 * Created by x36zhao on 2018/4/16.
 */
public final class SecurityContextHelper
{
    public static void validatePermission (Integer schoolId, Integer userId)
    {
        validateSchoolIdFromContext(schoolId);
        validateUserIdFromContext(userId);
    }

    private static void validateSchoolIdFromContext (Integer schoolId)
    {
        if (EntityUtils.isValidEntityId(schoolId))
        {
            JwtUser userDetails = getPrincipal();
            if (!schoolId.equals(userDetails.getSid()))
            {
                throw new ServiceException(String.format(
                        "Not allowed operation towards the data of school with id: %d and should be %d",
                        schoolId, userDetails.getSid()));
            }
        }
    }

    private static void validateUserIdFromContext (Integer userId)
    {
        if (EntityUtils.isValidEntityId(userId))
        {
            JwtUser userDetails = getPrincipal();
            if (!userId.equals(userDetails.getUid()))
            {
                throw new ServiceException(String.format(
                        "Not allowed operation towards the data of user with id: %d and should be %d",
                        userId, userDetails.getUid()));
            }
        }
    }

    public static boolean hasRole (String role)
    {
        JwtUser userDetails = getPrincipal();
        if (userDetails.getAuthorities() != null)
        {
            for (GrantedAuthority authority : userDetails.getAuthorities())
            {
                if (authority.getAuthority().equals(role))
                {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isSuperRootUser ()
    {
        return hasRole(EAuthorityName.ROLE_SUPERUSER.name());
    }

    public static JwtUser getPrincipal ()
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Objects.nonNull(principal);

        if (principal instanceof JwtUser)
        {
            return (JwtUser) principal;
        }

        throw new ServiceException(String.format("Illegal principal which should be JwtUser: %s", principal));
    }
}
