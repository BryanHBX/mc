package org.edu.timelycourse.mc.common.constants;

/**
 * Created by Marco on 2018/3/31.
 */
public final class Constants
{
    public static final String PAGE_ERROR                   = "500";
    public static final String PAGE_NOT_FOUND               = "404";
    public static final String PAGE_UNAUTHORIZED            = "401";
    public static final String REDIRECT_URL_PARAMETER       = "redirect";
    public static final Integer BUFF_SIZE                   = 1024;

    /**
     * upload folder
     */
    public static final String FOLDER_UPLOADS = "uploads";

    /**
     * temp folder
     */
    public static final String FOLDER_TEMP = "temp";

    /**
     * unknown identity
     */
    public static final String UNKONW_ID = String.valueOf(-1);

    /**
     * prefix of file resource
     */
    public static final String RESOURCE_FILE_PREFIX = System.getProperty("os.name")
            .toUpperCase().indexOf("WINDOWS") > -1 ? "file:/" : "file:";

    /**
     * asset resource path
     */
    public static final String ASSET_RESOURCE_PATH = "webapp/static/assets";

    /**
     * The default page size
     */
    public static Integer DEFAULT_PAGE_SIZE = Integer.valueOf(20);
}
