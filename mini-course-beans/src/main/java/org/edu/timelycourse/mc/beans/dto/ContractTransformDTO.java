package org.edu.timelycourse.mc.beans.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.edu.timelycourse.mc.common.utils.EntityUtils;

/**
 * Created by x36zhao on 2018/4/25.
 */
@Data
@JsonIgnoreProperties(value = { "id" })
public class ContractTransformDTO extends BaseDTO
{
    @JsonIgnore
    private Integer contractId;
    private Integer targetCourse;
    private Integer targetSubCourse;
    private double transformPeriod;

    @JsonIgnore
    private double sourcePeriod;

    private double transformPrice;

    @Override
    @JsonIgnore
    public boolean isValid ()
    {
        return EntityUtils.isValidEntityId(getSchoolId(), contractId, targetCourse, targetSubCourse) &&
                transformPeriod > 0 && transformPrice > 0;
    }
}
