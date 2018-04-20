package org.edu.timelycourse.mc.beans.enums;

public enum EPaymentType
{
    CASH       (1, "现金"),
    TRANSFER   (2, "转账"),
    WEIXIN     (3, "微信"),
    ALIPAY     (4, "支付宝"),
    OTHERS     (5, "其他");

    private Integer code;
    private String label;

    EPaymentType(Integer code, String label)
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
        for (EPaymentType item : EPaymentType.values())
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
        for (EPaymentType item : EPaymentType.values())
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
        for (EPaymentType item : EPaymentType.values())
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
