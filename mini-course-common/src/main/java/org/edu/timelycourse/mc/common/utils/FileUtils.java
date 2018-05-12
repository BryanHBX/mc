package org.edu.timelycourse.mc.common.utils;

import lombok.Data;
import org.edu.timelycourse.mc.common.constants.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by Marco on 2018/3/31.
 */
public final class FileUtils
{
    private static final String XLS_EXTENSION = ".xls";

    public static boolean isFileExists (String file)
    {
        return new File(file).exists();
    }

    public static List<FileEntry> listFiles (String folder)
    {
        List<FileEntry> fileEntries = new ArrayList<FileEntry>();
        File[] files = null;
        File fos = new File (folder);
        if (fos.exists() && fos.isDirectory())
        {
            files = fos.listFiles();
            // Arrays.sort(files, (a, b) -> Long.compare(a.lastModified(), b.lastModified()));  --> lambda in java8
            Arrays.sort(files, new Comparator<File>()
            {
                public int compare (File o1, File o2)
                {
                    return o1.lastModified() < o2.lastModified() ? 1  : -1;
                }
            });
        }

        if (files != null)
        {
            for (File file : files)
            {
                if (!file.isDirectory())
                {
                    fileEntries.add(new FileEntry(file.getName(), file.getPath(), file.length(), file.lastModified()));
                }
            }
        }

        return fileEntries;
    }

    @Data
    public static class FileEntry
    {
        private String name;
        private String path;
        private long length;
        private long lastModified;
        private String timestamp;

        public FileEntry () {}
        public FileEntry (String name, String path, long length, long lastModified)
        {
            this.name = name;
            this.path = path;
            this.length = length;
            this.lastModified = lastModified;

            Date date = new Date();
            date.setTime(this.lastModified);
            this.timestamp = DateUtils.getFormatedDate(date);
        }

    }

    /**
     * Returns the web class path
     *
     * @return web class path
     */
    public static String getWebClassPath()
    {
        String classPath = FileUtils.class.getResource("/").toString();
        if(classPath.indexOf(Constants.RESOURCE_FILE_PREFIX) > -1)
        {
            classPath = classPath.substring(Constants.RESOURCE_FILE_PREFIX.length());
        }
        return classPath;
    }

    public static String writeXlsFile (String dir, String fileName, byte[] bytes) throws IOException
    {
        String path = StringUtils.isNotEmpty(dir) ? dir : (getWebClassPath() + SeparatorUtils.getPathSeparator() + Constants.FOLDER_TEMP);
        File dirFile = new File (path);
        if (!dirFile.isDirectory())
        {
            dirFile.mkdirs();
        }

        File file = new File(makeFileName(path, fileName, XLS_EXTENSION));
        if (!file.exists())
        {
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes);
        fos.close();
        return file.getPath().replace(path + SeparatorUtils.getFileSeparator(), "");
    }

    private static String makeFileName (String path, String fileName, String ext)
    {
        StringBuilder builder = new StringBuilder();
        builder.append(path);
        builder.append(SeparatorUtils.getFileSeparator());
        builder.append(fileName);
        builder.append("_");
        builder.append(DateUtils.getFormatedDate(new Date(), "yyyyMMddHHmmss"));
        builder.append(ext);
        return builder.toString();
    }
}
