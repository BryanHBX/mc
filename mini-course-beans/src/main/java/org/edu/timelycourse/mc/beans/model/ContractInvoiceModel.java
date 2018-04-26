package org.edu.timelycourse.mc.beans.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;
import org.edu.timelycourse.mc.beans.dto.InvoiceDTO;
import org.edu.timelycourse.mc.beans.enums.EPaymentType;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.edu.timelycourse.mc.common.utils.StringUtil;
import org.edu.timelycourse.mc.common.utils.ValidatorUtil;
import org.springframework.beans.BeanUtils;
import sun.plugin.liveconnect.SecurityContextHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ContractInvoiceModel extends BaseModel
{
    /**
     * 合同ID
     */
    private Integer contractId;

    @JsonIgnore
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
     * 支付方式名称
     */
    public String getTypeName()
    {
        return type != null ? EPaymentType.getLabel(type) : null;
    }

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
        boolean valid = EPaymentType.hasValue(type) && EntityUtils.isValidEntityId(schoolId);

        if (valid)
        {
            if (!StringUtil.isNotEmpty(invoiceNo) && price > 0)
            {
                return false;
            }

            if (StringUtil.isNotEmpty(invoiceNo) && price <= 0)
            {
                return false;
            }
        }

       return valid;
    }

    public static ContractInvoiceModel from (final InvoiceDTO dto)
    {
        ContractInvoiceModel model = new ContractInvoiceModel();
        BeanUtils.copyProperties(dto, model);
        model.setType(dto.getPayType() != null ? dto.getPayType().getId() : null);
        model.setContractId(dto.getContract() != null ? dto.getContract().getId() : null);
        model.setOwnerId(dto.getOwner() != null ? dto.getOwner().getId() : null);
        return model;
    }

    public static List<ContractInvoiceModel> from (final List<InvoiceDTO> dtos)
    {
        List<ContractInvoiceModel> models = new ArrayList<>();
        if (dtos != null)
        {
            for (InvoiceDTO dto : dtos)
            {
                models.add(from(dto));
            }
        }
        return models;
    }
}
