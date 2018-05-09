package org.edu.timelycourse.mc.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by x36zhao on 2018/5/9.
 */
public final class FileUtils
{
    public static String writeFile (File dir, String fileName, byte[] bytes) throws IOException
    {
        File file = new File((dir != null ? dir.getPath() : "") + SeparatorUtils.getFileSeparator() + fileName);
        if (!file.exists())
        {
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes);
        fos.close();
        return file.getPath();
    }
}
