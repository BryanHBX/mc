package org.edu.timelycourse.mc.biz.model;

import lombok.Data;

import java.util.Date;

@Data
public class SchoolProductModel extends BaseEntity
{
    private String productName;
    private String productLabel;
    private int productType;

    private SchoolProductModel parent;
    private Integer schoolId;

    private Date creationTime;
    private Date lastUpdateTime;

    @Override
    public boolean isValidInput ()
    {
        return false;
    }
}
