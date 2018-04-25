package org.edu.timelycourse.mc.beans.dto;

import lombok.Data;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.edu.timelycourse.mc.common.utils.StringUtil;

/**
 * Created by x36zhao on 2018/4/25.
 */
@Data
public class ContractRefundDTO extends BaseDTO
{
    private Integer contractId;
    private double refundPeriod;
    private double refundPeriodPrice;
    private double refundOtherPrice;
    private String refundDate;

    public double getRefundPrice()
    {
        return refundPeriodPrice + refundOtherPrice;
    }

    @Override
    public boolean isValid ()
    {
        return EntityUtils.isValidEntityId(getSchoolId()) &&
                refundPeriod > 0 && refundPeriodPrice > 0 && StringUtil.isNotEmpty(refundDate);
    }
}
