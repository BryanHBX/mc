package org.edu.timelycourse.mc.biz.enums;

public enum EUserRole
{
    ADMINISTRATOR   (1, "管理员"),
    EMPLOYEE        (2, "普通职工"),
    SUPERUSER       (3, "平台管理员");

    private Integer code;
    private String label;

    EUserRole(Integer code, String label)
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
        for (EUserRole item : EUserRole.values())
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
        for (EUserRole item : EUserRole.values())
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
        for (EUserRole item : EUserRole.values())
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
