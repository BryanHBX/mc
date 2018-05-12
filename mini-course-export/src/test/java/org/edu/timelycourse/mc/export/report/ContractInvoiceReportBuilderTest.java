package org.edu.timelycourse.mc.export.report;

import org.edu.timelycourse.mc.beans.dto.ContractInvoiceDTO;
import org.edu.timelycourse.mc.beans.dto.NamedOptionProperty;
import org.edu.timelycourse.mc.common.utils.CommonUtils;
import org.edu.timelycourse.mc.common.utils.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by x36zhao on 2018/5/9.
 */
public class ContractInvoiceReportBuilderTest
{
    @Test
    public void build () throws Exception
    {
        String fileName = "invoiceReport.xls";
        List<ContractInvoiceDTO> invoices = new ArrayList<>();

        for (int i = 0; i < 10; i++)
        {
            ContractInvoiceDTO data = new ContractInvoiceDTO();
            data.setPayType(new NamedOptionProperty(1, "alipay"));
            data.setInvoiceNo(CommonUtils.createRandomNum(10));
            data.setPrice(Double.valueOf(CommonUtils.createRandomNum(4)));
            invoices.add(data);
        }

        ContractInvoiceReportBuilder reportBuilder = new ContractInvoiceReportBuilder();
        byte[] bytes = reportBuilder.build("report", invoices);
        Assert.assertNotNull(bytes);
        Assert.assertNotNull(reportBuilder.write(fileName, bytes));
    }
}
