package org.edu.timelycourse.mc.beans.dto;

import lombok.Data;
import org.edu.timelycourse.mc.common.utils.EntityUtils;

/**
 * Created by x36zhao on 2018/4/25.
 */
@Data
public class ContractTransformDTO extends BaseDTO
{
    private Integer sourceContract;
    private Integer targetCourse;
    private Integer targetSubCourse;
    private double transformPeriod;
    private double sourcePeriod;
    private double transformPrice;

    @Override
    public boolean isValid ()
    {
        return EntityUtils.isValidEntityId(getSchoolId(),
                sourceContract, targetCourse, targetSubCourse) &&
                transformPeriod > 0 && sourcePeriod > 0 && transformPrice > 0;
    }
}
