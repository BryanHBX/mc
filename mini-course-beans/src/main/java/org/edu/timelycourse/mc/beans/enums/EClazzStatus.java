package org.edu.timelycourse.mc.beans.enums;

/**
 * Created by marco on 2018/4/29
 */
public enum EClazzStatus
{
    NORMAL        (1, "正常"),
    TERMINATED    (2, "结束");

    private Integer code;
    private String label;

    EClazzStatus(Integer code, String label)
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
        for (EClazzStatus item : EClazzStatus.values())
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
        for (EClazzStatus item : EClazzStatus.values())
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
        for (EClazzStatus item : EClazzStatus.values())
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
        for (EClazzStatus item : EClazzStatus.values())
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
