package org.edu.timelycourse.mc.beans.criteria;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by x36zhao on 2018/4/20.
 */
@Data
public class BaseCriteria implements Serializable
{
    @JsonIgnore
    private Integer schoolId;
}
