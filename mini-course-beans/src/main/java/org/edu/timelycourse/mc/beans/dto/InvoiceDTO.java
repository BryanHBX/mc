package org.edu.timelycourse.mc.beans.dto;

import lombok.Data;
import org.edu.timelycourse.mc.beans.enums.EPaymentType;
import org.edu.timelycourse.mc.beans.model.ContractInvoiceModel;
import org.edu.timelycourse.mc.beans.paging.PagingBean;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by x36zhao on 2018/4/19.
 */
@Data
public class InvoiceDTO extends BaseDTO
{
    /**
     * 收据编号
     */
    private String invoiceNo;

    /**
     * 缴费金额
     */
    private double price;

    /**
     * 支付类型
     */
    //private String type;

    /**
     * 报名类型
     */
    private NamedOptionProperty payType;

    /**
     * 缴费日期
     */
    private Date creationTime;

    /**
     * 合同信息
     */
    private ContractDTO contract;

    /**
     * 归属者
     */
    private NamedOptionProperty owner;

    public static PagingBean<InvoiceDTO> from (PagingBean<ContractInvoiceModel> pagingBean)
    {
        PagingBean<InvoiceDTO> result = new PagingBean<>();
        result.setItems(from(pagingBean.getItems()));
        result.setPageNumber(pagingBean.getPageNumber());
        result.setPageSize(pagingBean.getPageSize());
        result.setTotalItems(pagingBean.getTotalItems());
        result.setTotalPageNumber(pagingBean.getTotalPageNumber());
        return result;
    }

    public static List<InvoiceDTO> from (List<ContractInvoiceModel> models)
    {
        List<InvoiceDTO> result = new ArrayList<>();
        for (ContractInvoiceModel model : models)
        {
            result.add(from(model));
        }
        return result;
    }

    public static InvoiceDTO from (ContractInvoiceModel model)
    {
        try
        {
            if (model != null)
            {
                InvoiceDTO dto = new InvoiceDTO();
                BeanUtils.copyProperties(model, dto);
                dto.setContract(ContractDTO.from(model.getContract()));
                dto.setPayType(new NamedOptionProperty(model.getType(), EPaymentType.getLabel(model.getType())));
                return dto;
            }
            return null;
        }
        catch (Exception ex)
        {
            throw new RuntimeException(String.format(
                    "Failed to copy properties from contract (%s) to DTO object", model
            ), ex);
        }
    }

    @Override
    public boolean isValid ()
    {
        return false;
    }
}
