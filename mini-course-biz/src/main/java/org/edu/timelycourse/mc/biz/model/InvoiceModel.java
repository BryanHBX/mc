package org.edu.timelycourse.mc.biz.model;

import lombok.Data;
import org.edu.timelycourse.mc.biz.model.BaseEntity;

@Data
public class InvoiceModel extends BaseEntity
{
    /**
     * 合同ID
     */
    private Integer contractId;

    /**
     * 收据编号
     */
    private String invoiceNo;

    /**
     * 支付金额
     */
    private double price;

    /**
     * 支付方式
     */
    private Integer type;

    @Override
    public boolean isValid()
    {
        return true;
    }
}
