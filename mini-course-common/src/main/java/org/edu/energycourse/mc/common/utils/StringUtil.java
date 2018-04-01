package org.edu.energycourse.mc.common.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class StringUtil
{
    /**
     * Joins the specified list of strings with defaut separator ","
     *
     * @param strs list of strings to be joined
     * @return the string joined with the separator ","
     */
    public static String join (Object[] strs)
    {
        return join(strs, ",");
    }

    /**
     * Joins the specified list of strings
     * with provided join separator charactor
     *
     * @param strs list of strings to be joined
     * @param join the charactor used to join the strings
     * @return the string joined with the separator ","
     */
    public static String join (Object[] strs, String join)
    {
        StringBuilder sb = new StringBuilder();
        if (strs != null)
        {
            for (int i = 0, size = strs.length; i < size; i++)
            {
                sb.append(strs[i]);
                if (i < size - 1)
                {
                    sb.append(join);
                }
            }
        }
        return sb.toString();
    }

    /**
     * Joins the specified list of strings
     * with provided join separator charactor
     *
     * @param strs list of strings to be joined
     * @return the string joined with the separator ","
     */
    public static String join (Collection<String> strs)
    {
        return join(strs, ",");
    }


    /**
     * Joins the specified list of strings
     * with provided join separator charactor
     *
     * @param strs list of strings to be joined
     * @param join the charactor used to join the strings
     * @return the string joined with the separator ","
     */
    public static String join (Collection<String> strs, String join)
    {
        return strs != null ? join(strs.toArray(), join) : null;
    }

    /**
     * Returns the specified string is not empty
     *
     * @param text the string
     * @return true if the specified string not empty
     */
    public static boolean isNotEmpty (final String text)
    {
        return text != null && !"".equals(text.trim());
    }

    /**
     * Returns the specified list of strings is not empty
     *
     * @param strings the list of strings
     * @return true if the specified strings not empty
     */
    public static boolean isNotEmpty (final String... strings)
    {
        if (strings != null && strings.length > 0)
        {
            for (String string : strings)
            {
                if (!isNotEmpty(string))
                {
                    return false;
                }
            }
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * Only for local test, should be removed after deploy.
     *
     * @param str
     * @return
     */
    public static String revertUnicodeChars (String str)
    {
        str = str.replace("%20", " ");
        str = str.replace("%2F", "/");
        return str;
    }

    /**
     * Converts the string list to set collection.
     *
     * @param strs the string array list
     * @return set collection
     */
    public static Set<String> convertToSet (final String[] strs)
    {
        if (strs != null && strs.length > 0)
        {
            Set<String> sets = new HashSet<String>();
            for (String str : strs)
            {
                sets.add(str);
            }

            return sets;
        }

        return null;
    }

    /**
     * Converts the quot
     *
     * @param paramString
     * @return
     */
    public static String convertQuot (String paramString)
    {
        return paramString.replace("'", "\\'").replace("\"", "\\\"");
    }

    /**
     * @param input
     * @return
     * @author Zhao.Xiang
     */
    public static String filter (String input)
    {
        String tmp = input;
        tmp = tmp.replace("´", "'");
        tmp = tmp.replace("”", "\"");
        tmp = tmp.replace("“", "\"");
        tmp = tmp.replace("‘", "'");
        return tmp;
    }

    /**
     * Returns true if the specified string is numberic
     *
     * @param paramString
     * @return
     */
    public static boolean isNumeric (String paramString)
    {
        int i = paramString.length();
        while (true)
        {
            i--;
            if (i < 0)
            {
                break;
            }
            if (!Character.isDigit(paramString.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }
}