package org.edu.timelycourse.mc.biz.dto;

import lombok.Data;
import org.edu.timelycourse.mc.biz.model.InvoiceModel;
import org.edu.timelycourse.mc.biz.paging.PagingBean;
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
    private String type;

    /**
     * 缴费日期
     */
    private Date creationTime;

    /**
     * 合同信息
     */
    private ContractDTO contract;

    public static PagingBean<InvoiceDTO> from (PagingBean<InvoiceModel> pagingBean)
    {
        PagingBean<InvoiceDTO> result = new PagingBean<>();
        result.setItems(from(pagingBean.getItems()));
        result.setPageNumber(pagingBean.getPageNumber());
        result.setPageSize(pagingBean.getPageSize());
        result.setTotalItems(pagingBean.getTotalItems());
        result.setTotalPageNumber(pagingBean.getTotalPageNumber());
        return result;
    }

    public static List<InvoiceDTO> from (List<InvoiceModel> models)
    {
        List<InvoiceDTO> result = new ArrayList<>();
        for (InvoiceModel model : models)
        {
            result.add(from(model));
        }
        return result;
    }

    public static InvoiceDTO from (InvoiceModel model)
    {
        try
        {
            if (model != null)
            {
                InvoiceDTO dto = new InvoiceDTO();
                BeanUtils.copyProperties(model, dto);
                dto.setContract(ContractDTO.from(model.getContract()));
                return dto;
            }
            return null;
        }
        catch (Exception ex)
        {
            throw new RuntimeException(String.format(
                    "Failed to copy properties from contract (%s) to VO object", model
            ), ex);
        }
    }
}
