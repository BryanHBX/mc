package org.edu.timelycourse.mc.beans.model;

import lombok.Data;
import org.apache.logging.log4j.util.Strings;
import org.edu.timelycourse.mc.beans.dto.InvoiceDTO;
import org.edu.timelycourse.mc.beans.enums.EPaymentType;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.edu.timelycourse.mc.common.utils.ValidatorUtil;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
public class InvoiceModel extends BaseModel
{
    /**
     * 合同ID
     */
    private Integer contractId;
    private ContractModel contract;

    /**
     * 学校ID
     */
    private Integer schoolId;

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

    /**
     * 归属者
     */
    private Integer ownerId;

    @Override
    public boolean isValidInput ()
    {
        return EPaymentType.hasValue(type) &&
                ValidatorUtil.isFloatNumber(price) &&
                EntityUtils.isValidEntityId(schoolId) &&
                Strings.isNotEmpty(invoiceNo);
    }

    @Override
    public String getUrlParams()
    {
        return null;
    }

    public static InvoiceModel from (final InvoiceDTO dto)
    {
        InvoiceModel model = new InvoiceModel();
        BeanUtils.copyProperties(dto, model);
        return model;
    }
}
