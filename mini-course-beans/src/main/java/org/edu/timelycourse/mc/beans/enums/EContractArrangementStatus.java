package org.edu.timelycourse.mc.beans.enums;

public enum EContractArrangementStatus
{
    TODO        (0, "待安排"),
    PLANNED     (1, "已安排"),
    FINISHED    (2, "已结束");

    private Integer code;
    private String label;

    EContractArrangementStatus(Integer code, String label)
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
        for (EContractArrangementStatus item : EContractArrangementStatus.values())
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
        for (EContractArrangementStatus item : EContractArrangementStatus.values())
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
        for (EContractArrangementStatus item : EContractArrangementStatus.values())
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
        for (EContractArrangementStatus item : EContractArrangementStatus.values())
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
