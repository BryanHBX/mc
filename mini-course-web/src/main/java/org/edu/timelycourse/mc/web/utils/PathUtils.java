package org.edu.timelycourse.mc.web.utils;

import org.edu.timelycourse.mc.common.constants.Constants;
import org.edu.timelycourse.mc.common.utils.SeparatorUtils;

import java.io.File;

/**
 * Created by marco on 2018/5/12
 */
public final class PathUtils
{
    public static String getAssetsPath ()
    {
        String classPath = PathUtils.class.getResource("/").toString();
        if(classPath.indexOf(Constants.RESOURCE_FILE_PREFIX) > -1)
        {
            classPath = classPath.substring(Constants.RESOURCE_FILE_PREFIX.length());
        }

        String assetPath = classPath + SeparatorUtils.getFileSeparator() + Constants.ASSET_RESOURCE_PATH;
        if (assetPath.contains("%20"))
        {
            assetPath = assetPath.replaceAll("%20", " ");
        }
        return assetPath;
    }

    public static String getAssetTempPath ()
    {
        String assetPath = getAssetsPath();
        String tempPath = assetPath + SeparatorUtils.getFileSeparator() + Constants.FOLDER_TEMP + SeparatorUtils.getFileSeparator();
        File file = new File (tempPath);
        if (!file.isDirectory())
        {
            file.mkdirs();
        }
        return file.getPath();
    }
}
