package org.edu.timelycourse.mc.beans.enums;

public enum EStudentStatus
{
    ONGOING     (1, "在读"),
    FINISHED    (2, "老生"),
    SUSPENDED   (3, "暂停");

    private Integer code;
    private String label;

    EStudentStatus (Integer code, String label)
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
        for (EStudentStatus item : EStudentStatus.values())
        {
            if (item.name().equals(name))
            {
                return item.label;
            }
        }

        return name;
    }

    public static String getLabel(Integer code)
    {
        for (EStudentStatus item : EStudentStatus.values())
        {
            if (item.code().equals(code))
            {
                return item.label;
            }
        }

        return null;
    }

    public static Integer getCode(String name)
    {
        for (EStudentStatus item : EStudentStatus.values())
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
        for (EStudentStatus item : EStudentStatus.values())
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
