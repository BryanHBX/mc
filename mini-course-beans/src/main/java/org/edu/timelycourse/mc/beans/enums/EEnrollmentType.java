package org.edu.timelycourse.mc.beans.enums;

public enum EEnrollmentType
{
    FRESHER     (1, "新报"),
    CONTINUED   (2, "续保"),
    TRANSFER    (3, "转课时");

    private Integer code;
    private String label;

    EEnrollmentType(Integer code, String label)
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
        for (EEnrollmentType item : EEnrollmentType.values())
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
        for (EEnrollmentType item : EEnrollmentType.values())
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
        for (EEnrollmentType item : EEnrollmentType.values())
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
        for (EEnrollmentType item : EEnrollmentType.values())
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
