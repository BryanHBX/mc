package org.edu.timelycourse.mc.biz.enums;

public enum EBuiltInConfig
{
    STUDENT_LEVEL     ("C_STU_LEVEL",       "学生年段"),
    STUDENT_SUBLEVEL  ("C_STU_SUB_LEVEL",   "细分年段"),
    COURSE_TYPE       ("C_CRS_TYPE",        "课程名称"),
    COURSE_SUBTYPE    ("C_CRS_SUB_TYPE",    "课程子类");

    private String code;
    private String label;

    EBuiltInConfig(String code, String label)
    {
        this.code = code;
        this.label = label;
    }

    public String code()
{
    return this.code;
}

    public String label()
    {
        return this.label;
    }

    public static String getLabel(String name)
    {
        for (EBuiltInConfig item : EBuiltInConfig.values())
        {
            if (item.name().equals(name))
            {
                return item.label;
            }
        }

        return name;
    }

    public static String getCode(String name)
    {
        for (EBuiltInConfig item : EBuiltInConfig.values())
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
        for (EBuiltInConfig item : EBuiltInConfig.values())
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
