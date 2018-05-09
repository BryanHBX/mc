package org.edu.timelycourse.mc.export.report;

import org.edu.timelycourse.mc.beans.dto.BaseDTO;
import org.edu.timelycourse.mc.beans.dto.ContractInvoiceDTO;
import org.edu.timelycourse.mc.export.excel.ExcelBuilder;
import org.edu.timelycourse.mc.export.excel.ExcelBuilder.ExcelColumnBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by x36zhao on 2018/5/9.
 */
public class ContractInvoiceReportBuilder implements ReportBuilder
{
    @Override
    public byte[] build (String sheetName, Collection<? extends BaseDTO> collection) throws Exception
    {
        ExcelBuilder<ContractInvoiceDTO> builder = ExcelBuilder.prepareExcel(ContractInvoiceDTO.class).sheetName(sheetName);

        // prepare columns
        builder
                .addColumn("InvoiceNo").generatedBy(ContractInvoiceDTO::getInvoiceNo)
                .addColumn("Amount").generatedBy(ContractInvoiceDTO::getPrice)
                .addColumn("Type").generatedBy(ContractInvoiceDTO::getPayTypeName);

        return builder.build((List<ContractInvoiceDTO>)collection);
    }

    @Override
    public void buildColumns (ExcelBuilder builder1)
    {
        ExcelBuilder<ContractInvoiceDTO> builder = ExcelBuilder.prepareExcel(ContractInvoiceDTO.class).sheetName("");
        builder.addColumn("").generatedBy(ContractInvoiceDTO::getInvoiceNo);

        builder
                .addColumn("InvoiceNo").generatedBy(ContractInvoiceDTO::getInvoiceNo)
                .addColumn("Amount").generatedBy(ContractInvoiceDTO::getPrice)
                .addColumn("Type").generatedBy(ContractInvoiceDTO::getPayTypeName);
    }
}
