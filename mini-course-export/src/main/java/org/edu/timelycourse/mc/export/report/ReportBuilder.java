package org.edu.timelycourse.mc.export.report;

import org.edu.timelycourse.mc.beans.dto.BaseDTO;
import org.edu.timelycourse.mc.common.utils.FileUtils;

import java.util.Collection;

/**
 * Created by x36zhao on 2018/5/9.
 */
public interface ReportBuilder
{
    byte[] build (String sheetName, Collection<? extends BaseDTO> collection)  throws Exception;

    default String write (String path, String fileName, byte[] bytes) throws Exception
    {
        return FileUtils.writeXlsFile(path, fileName, bytes);
    }
}
