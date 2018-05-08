package org.edu.timelycourse.mc.common.utils;

import java.util.Random;

/**
 * Created by x36zhao on 2018/5/8.
 */
public final class CommonUtils
{
    public static String createRandomNum(int size)
    {
        Random d = new Random();
        StringBuffer str = new StringBuffer();
        for (int m = 0; m < size; m++)
        {
            str.append(d.nextInt(10));
        }
        return str.toString();
    }
}
