package org.edu.timelycourse.mc.beans.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.edu.timelycourse.mc.common.utils.StringUtil;

import java.util.Date;

/**
 * Created by x36zhao on 2018/4/25.
 */
@Data
@JsonIgnoreProperties(value = { "id" })
public class ContractRefundDTO extends BaseDTO
{
    private Integer contractId;
    private double refundPeriod;
    private double refundPeriodPrice;
    private double refundOtherPrice;
    private Date refundDate;

    public double getRefundPrice()
    {
        return refundPeriodPrice + refundOtherPrice;
    }

    @Override
    @JsonIgnore
    public boolean isValid ()
    {
        return EntityUtils.isValidEntityId(getSchoolId()) &&
                refundPeriod > 0 && refundPeriodPrice > 0 && refundDate != null;
    }
}
