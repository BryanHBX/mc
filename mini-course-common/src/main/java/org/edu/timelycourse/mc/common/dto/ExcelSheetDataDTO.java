package org.edu.timelycourse.mc.common.dto;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marco on 2018/3/31.
 */
@Data
@ToString
public class ExcelSheetDataDTO extends BaseDTO
{
    private int sheetName;
    private int sheetIndex;
    private List<HeaderColumn> headerColumns;
    private List<Row> rows;

    public void addRow (final Row row)
    {
        if (rows == null)
        {
            rows = new ArrayList<Row>();
        }

        if (row != null)
        {
            rows.add(row);
        }
    }

    public int getHeaderColumnIndexByName (String columnName)
    {
        if (headerColumns != null && !"".equals(columnName))
        {
            for (HeaderColumn headerColumn : headerColumns)
            {
                if (headerColumn.getColumnName().equals(columnName))
                {
                    return headerColumn.getColumnIndex();
                }
            }
        }

        return -1;
    }

    @Data
    @ToString
    public static class HeaderColumn
    {
        private int columnIndex;
        private String columnName;

        public HeaderColumn()
        {
        }

        public HeaderColumn(int columnIndex, String columnName)
        {
            this.columnIndex = columnIndex;
            this.columnName = columnName;
        }
    }

    @Data
    @ToString
    public static class Row
    {
        private int rowIndex;
        private List<Cell> cells;

        public void addCell (final Cell cell)
        {
            if (cells == null)
            {
                cells = new ArrayList<Cell>();
            }

            if (cell != null)
            {
                cells.add(cell);
            }
        }

        public Object getCellValue (int cellIndex)
        {
            if (cells != null && cells.size() > cellIndex)
            {
                return cells.get(cellIndex).getCellValue();
            }

            return null;
        }

    }

    @Data
    @ToString
    public static class Cell
    {
        private int cellIndex;
        private Object cellValue;

        public Cell ()
        {
        }

        public Cell (int cellIndex, Object cellValue)
        {
            this.cellIndex = cellIndex;
            this.cellValue = cellValue;
        }

    }
}
