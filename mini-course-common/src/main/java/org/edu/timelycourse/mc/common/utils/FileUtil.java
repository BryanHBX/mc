package org.edu.timelycourse.mc.common.utils;

import lombok.Data;

import java.io.File;
import java.util.*;

/**
 * Created by Marco on 2018/3/31.
 */
public final class FileUtil
{
    private static final String TEMPORARY_FILE_EXTENSION = "tmp";

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
            this.timestamp = DateUtil.getFormatedDate(date);
        }

    }
}
