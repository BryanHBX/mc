package org.edu.timelycourse.mc.biz.model;

import lombok.Data;
import org.apache.logging.log4j.util.Strings;
import org.edu.timelycourse.mc.biz.enums.EPaymentType;
import org.edu.timelycourse.mc.biz.model.BaseEntity;

import java.util.Date;

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

    /**
     * 收据添加时间
     */
    private Date creationTime;

    /**
     * 收据更新时间
     */
    private Date lastUpdateTime;

    @Override
    public boolean isValidInput ()
    {
        return EPaymentType.hasValue(type) && price > 0 && Strings.isNotEmpty(invoiceNo);
    }
}
