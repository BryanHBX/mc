package org.edu.timelycourse.mc.export.excel;

import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by x36zhao on 2018/5/9.
 */
public final class ExcelBuilder<T>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelBuilder.class);

    private Class<T> protoType;

    private String sheetName = "report";

    private List<ExcelColumnBuilder> columnBuilders = new ArrayList<>();

    private ExcelBuilder (Class<T> protoType)
    {
        this.protoType = protoType;
    }

    public static <T> ExcelBuilder<T> prepareExcel (Class<T> protoType)
    {
        return new ExcelBuilder<>(protoType);
    }

    public ExcelColumnBuilder addColumn (String columnName)
    {
        ExcelColumnBuilder columnBuilder = new ExcelColumnBuilder(columnName);
        columnBuilders.add(columnBuilder);
        return columnBuilder;
    }

    public String getSheetName()
    {
        return sheetName;
    }

    public ExcelBuilder<T> sheetName (String sheetName)
    {
        this.sheetName = sheetName;
        return this;
    }

    public byte[] build (List<T> dataList) throws IOException
    {
        HSSFWorkbook workbook = createWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);
        sheet.createFreezePane(0, 1);

        createTableHeader(sheet);

        createTableContent(sheet, dataList);

        return workbookToBytes(workbook);
    }

    private byte[] workbookToBytes (HSSFWorkbook workbook) throws IOException
    {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try
        {
            workbook.write(byteArrayOutputStream);
        }
        catch (IOException e)
        {
            LOGGER.warn("Failed to write workbook for type: " + protoType.getName(), e);
            throw e;
        }

        return byteArrayOutputStream.toByteArray();
    }

    private void createTableContent (HSSFSheet sheet, List<T> dataList)
    {
        for (int index = 0; index < dataList.size(); index++)
        {
            HSSFRow row = sheet.createRow(index + 1); // +1 for header row
            T data = dataList.get(index);
            for (int columnIndex = 0; columnIndex < columnBuilders.size(); columnIndex++)
            {
                ExcelColumnBuilder builder = columnBuilders.get(columnIndex);

                Object appliedData = builder.getGenerator().apply(data);
                String value = appliedData != null ? appliedData.toString() : null;
                row.createCell(columnIndex).setCellValue(Optional.ofNullable(value).orElse(""));
            }
        }
    }

    private void createTableHeader (HSSFSheet sheet)
    {
        List<String> headers = columnBuilders.stream().map(builder -> builder.getColumnName()).collect(Collectors.toList());
        HSSFRow row = sheet.createRow(0);
        for (String head : headers)
        {
            row.createCell(headers.indexOf(head)).setCellValue(head);
        }
    }

    private HSSFWorkbook createWorkbook ()
    {
        final HSSFWorkbook workbook = new HSSFWorkbook();
        final HSSFCreationHelper creationHelper = workbook.getCreationHelper();
        final HSSFCellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("YY-MM-DD"));
        return workbook;
    }

    public final class ExcelColumnBuilder
    {
        private String columnName;
        private Function<T, Object> generator;

        public ExcelColumnBuilder (String columnName)
        {
            this.columnName = columnName;
        }

        public ExcelBuilder<T> generatedBy (Function<T, Object> generator)
        {
            this.generator = generator;
            return ExcelBuilder.this;
        }

        protected String getColumnName ()
        {
            return columnName;
        }

        protected Function<T, Object> getGenerator ()
        {
            return generator;
        }
    }
}