package org.edu.timelycourse.mc.export.report;

import org.edu.timelycourse.mc.beans.dto.BaseDTO;
import org.edu.timelycourse.mc.export.excel.ExcelBuilder;
import org.edu.timelycourse.mc.export.excel.ExcelBuilder.ExcelColumnBuilder;

import java.util.Collection;
import java.util.List;

/**
 * Created by x36zhao on 2018/5/9.
 */
public interface ReportBuilder
{
    byte[] build (String sheetName, Collection<? extends BaseDTO> collection)  throws Exception;

    void buildColumns(ExcelBuilder builder);
}
