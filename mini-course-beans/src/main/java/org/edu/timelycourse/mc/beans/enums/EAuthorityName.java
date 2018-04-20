package org.edu.timelycourse.mc.beans.enums;

public enum EAuthorityName
{
    ROLE_TEACHER        (1, "教师"),
    ROLE_CONSULTANT     (2, "咨询师"),
    ROLE_ACADEMIC_DEAN  (3, "教务员"),
    ROLE_TREASURER      (4, "财务"),
    ROLE_ADMINISTRATOR  (5, "管理员"),
    ROLE_SUPERUSER      (6, "超级管理员");

    private Integer code;
    private String label;

    EAuthorityName(Integer code, String label)
    {
        this.code = code;
        this.label = label;
    }

    public Integer code()
{
    return this.code;
}

    public String label()
    {
        return this.label;
    }

    public static String getLabel(String name)
    {
        for (EAuthorityName item : EAuthorityName.values())
        {
            if (item.name().equals(name))
            {
                return item.label;
            }
        }

        return name;
    }

    public static Integer getCode(String name)
    {
        for (EAuthorityName item : EAuthorityName.values())
        {
            if (item.name().equals(name))
            {
                return item.code;
            }
        }
        return null;
    }

    public static boolean hasValue (Integer code)
    {
        for (EAuthorityName item : EAuthorityName.values())
        {
            if (item.code().equals(code))
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString()
    {
        return this.name();
    }
}
