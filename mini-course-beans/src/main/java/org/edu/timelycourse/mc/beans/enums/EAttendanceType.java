package org.edu.timelycourse.mc.beans.enums;

/**
 * Created by marco on 2018/4/29
 */
public enum EAttendanceType
{
    NORMAL        (1, "正常"),
    EXCHANGED     (2, "补课折算");

    private Integer code;
    private String label;

    EAttendanceType (Integer code, String label)
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
        for (EAttendanceType item : EAttendanceType.values())
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
        for (EAttendanceType item : EAttendanceType.values())
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
        for (EAttendanceType item : EAttendanceType.values())
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
        for (EAttendanceType item : EAttendanceType.values())
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
