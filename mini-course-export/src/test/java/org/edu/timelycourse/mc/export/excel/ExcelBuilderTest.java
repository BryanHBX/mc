package org.edu.timelycourse.mc.export.excel;

import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by x36zhao on 2018/5/9.
 */
public class ExcelBuilderTest
{
    @Test
    public void build() throws Exception
    {
        String sheetName = "report";
        ExcelBuilder<Report> builder = ExcelBuilder.prepareExcel(Report.class).sheetName(sheetName);
        Assert.assertEquals(sheetName, builder.getSheetName());

        builder.addColumn("column").generatedBy(Report::getDate);
        List<Report> reports = new ArrayList<>();
        reports.add(new Report("test", "test"));
        byte[] bytes = builder.build(reports);
        Assert.assertNotNull(bytes);
    }

    @Data
    private class Report
    {
        private String date;
        private String value;

        public Report (String date, String value)
        {
            this.date = date;
            this.value = value;
        }
    }
}
