package org.edu.energycourse.mc.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Marco on 2018/3/31.
 */
public class DateUtil
{
    private static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static SimpleDateFormat defaultDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

    public static String getFormatedDate (final Date date)
    {
        return defaultDateFormat.format(date);
    }

    public static String getFormatedDate (final Date date, String format)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
}
