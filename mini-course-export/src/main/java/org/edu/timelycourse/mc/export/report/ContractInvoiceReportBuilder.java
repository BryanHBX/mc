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

        // build column in code level as one short term solution due to limited time,
        // from long term solution, column must come from external asset resource, for instance, property file or YAML file.
        builder
                .addColumn("报名类型").generatedBy(ContractInvoiceDTO::getStatusName)
                .addColumn("缴费日期").generatedBy(ContractInvoiceDTO::getCreationTime)
                .addColumn("姓名").generatedBy(ContractInvoiceDTO::getStudentName)
                .addColumn("年级").generatedBy(ContractInvoiceDTO::getGradeName)
                .addColumn("报名课程").generatedBy(ContractInvoiceDTO::getCourseName)
                .addColumn("缴费金额").generatedBy(ContractInvoiceDTO::getPrice)
                .addColumn("收据号").generatedBy(ContractInvoiceDTO::getInvoiceNo)
                .addColumn("咨询师").generatedBy(ContractInvoiceDTO::getSupervisorName)
                .addColumn("缴费类型").generatedBy(ContractInvoiceDTO::getPayTypeName);

        return builder.build((List<ContractInvoiceDTO>) collection);
    }
}
