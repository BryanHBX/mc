package org.edu.timelycourse.mc.biz.enums;

public enum EVisible
{
    VISIBLE      (1, "UI可见"),
    INVISIBLE    (0, "UI不可见");

    private Integer code;
    private String label;

    EVisible(Integer code, String label)
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
        for (EVisible item : EVisible.values())
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
        for (EVisible item : EVisible.values())
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
        for (EVisible item : EVisible.values())
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
