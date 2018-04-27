package org.edu.timelycourse.mc.beans.enums;

public enum EInvoiceStatus
{
    FRESHER     (1, "新报"),
    CONTINUED   (2, "续保"),
    REFUND      (3, "退费");

    private Integer code;
    private String label;

    EInvoiceStatus(Integer code, String label)
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
        for (EInvoiceStatus item : EInvoiceStatus.values())
        {
            if (item.name().equals(name))
            {
                return item.label;
            }
        }

        return name;
    }

    public static String getLabel (Integer code)
    {
        for (EInvoiceStatus item : EInvoiceStatus.values())
        {
            if (item.code().equals(code))
            {
                return item.label();
            }
        }
        return null;
    }

    public static Integer getCode(String name)
    {
        for (EInvoiceStatus item : EInvoiceStatus.values())
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
        for (EInvoiceStatus item : EInvoiceStatus.values())
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
