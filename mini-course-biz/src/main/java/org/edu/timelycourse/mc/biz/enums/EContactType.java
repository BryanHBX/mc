package org.edu.timelycourse.mc.biz.enums;

public enum EContactType
{
    FATHER    (1, "父亲"),
    MOTHER    (2, "母亲"),
    GRANDPA   (3, "爷爷"),
    GRANDMA   (4, "奶奶"),
    PERSONAL  (5, "本人"),
    OTHER     (6, "其他");

    private Integer code;
    private String label;

    EContactType(Integer code, String label)
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
        for (EContactType item : EContactType.values())
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
        for (EContactType item : EContactType.values())
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
        for (EContactType item : EContactType.values())
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
