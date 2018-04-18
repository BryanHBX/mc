package org.edu.timelycourse.mc.biz.enums;

public enum EContractDebtStatus
{
    ARREARAGE   (1, "欠费"),
    DONE        (0, "结清");

    private Integer code;
    private String label;

    EContractDebtStatus(Integer code, String label)
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
        for (EContractDebtStatus item : EContractDebtStatus.values())
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
        for (EContractDebtStatus item : EContractDebtStatus.values())
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
        for (EContractDebtStatus item : EContractDebtStatus.values())
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
