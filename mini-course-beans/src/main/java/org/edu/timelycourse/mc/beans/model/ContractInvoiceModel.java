package org.edu.timelycourse.mc.beans.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.edu.timelycourse.mc.beans.dto.ContractInvoiceDTO;
import org.edu.timelycourse.mc.beans.enums.EPaymentType;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.edu.timelycourse.mc.common.utils.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 发票收据
 */
@Data
public class ContractInvoiceModel extends BaseModel
{
    /**
     * 合同ID
     */
    private Integer contractId;

    /**
     * 学校ID
     */
    @JsonIgnore
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
     * 收据更新时间
     */
    private Date lastUpdateTime;

    /**
     * 归属者
     */
    private Integer ownerId;

    /**
     * 收据添加时间
     */
    @JsonIgnore
    private Date creationTime;

    @JsonIgnore
    private ContractModel contract;

    /**
     * 收据类型
     */
    private Integer status;

    @Override
    public boolean isValidInput ()
    {
        boolean valid = EPaymentType.hasValue(type) && EntityUtils.isValidEntityId(schoolId);

        if (valid)
        {
            if (!StringUtils.isNotEmpty(invoiceNo) && price > 0)
            {
                return false;
            }

            if (StringUtils.isNotEmpty(invoiceNo) && price <= 0)
            {
                return false;
            }
        }

       return valid;
    }

    /**
     * 获取支付方式名称
     */
    public String getTypeName()
    {
        return type != null ? EPaymentType.getLabel(type) : null;
    }

    public static ContractInvoiceModel from (final ContractInvoiceDTO dto)
    {
        ContractInvoiceModel model = new ContractInvoiceModel();
        BeanUtils.copyProperties(dto, model);
        model.setType(dto.getPayType() != null ? dto.getPayType().getId() : null);
        model.setContractId(dto.getContract() != null ? dto.getContract().getId() : null);
        model.setOwnerId(dto.getOwner() != null ? dto.getOwner().getId() : null);
        return model;
    }

    public static List<ContractInvoiceModel> from (final List<ContractInvoiceDTO> dtos)
    {
        List<ContractInvoiceModel> models = new ArrayList<>();
        if (dtos != null)
        {
            for (ContractInvoiceDTO dto : dtos)
            {
                models.add(from(dto));
            }
        }
        return models;
    }
}
