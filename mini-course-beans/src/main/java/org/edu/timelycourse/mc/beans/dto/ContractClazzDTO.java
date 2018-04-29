package org.edu.timelycourse.mc.beans.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.edu.timelycourse.mc.beans.model.ContractClazzModel;
import org.edu.timelycourse.mc.beans.paging.PagingBean;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.edu.timelycourse.mc.common.utils.StringUtil;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by x36zhao on 2018/4/26.
 */
@Data
public class ContractClazzDTO extends BaseDTO
{
    private Integer contractId;
    private Integer teacherId;
    private String name;

    @JsonIgnore
    private Integer schoolId;

    private Integer id;
    private Integer status;

    @Override
    @JsonIgnore
    public boolean isValid ()
    {
        return EntityUtils.isValidEntityId(contractId, teacherId, schoolId) && StringUtil.isNotEmpty(name);
    }

    public static PagingBean<ContractClazzDTO> from (PagingBean<ContractClazzModel> pagingBean)
    {
        PagingBean<ContractClazzDTO> result = new PagingBean<>();
        result.setItems(from(pagingBean.getItems()));
        result.setPageNumber(pagingBean.getPageNumber());
        result.setPageSize(pagingBean.getPageSize());
        result.setTotalItems(pagingBean.getTotalItems());
        result.setTotalPageNumber(pagingBean.getTotalPageNumber());
        return result;
    }

    public static List<ContractClazzDTO> from (List<ContractClazzModel> models)
    {
        List<ContractClazzDTO> result = new ArrayList<>();
        for (ContractClazzModel model : models)
        {
            result.add(from(model));
        }
        return result;
    }


    public static ContractClazzDTO from (ContractClazzModel model)
    {
        try
        {
            if (model != null)
            {
                ContractClazzDTO dto = new ContractClazzDTO();
                BeanUtils.copyProperties(model, dto);
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
