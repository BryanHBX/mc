package org.edu.timelycourse.mc.beans.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.edu.timelycourse.mc.beans.paging.PagingBean;

/**
 * Created by marco on 2018/4/27
 */
@Data
@JsonIgnoreProperties(value = { "id", "schoolId" })
public class ContractInvoiceStatDTO extends BaseDTO
{
    private PagingBean<ContractInvoiceDTO> invoices;
    private double totalIncome;
    private double totalRefund;

    @Override
    @JsonIgnore
    public boolean isValid()
    {
        return true;
    }
}
