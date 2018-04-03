package org.edu.energycourse.mc.biz.entity.school;

import lombok.Data;
import org.edu.energycourse.mc.biz.entity.BaseEntity;

@Data
public class SchoolProduct extends BaseEntity
{
    private String productName;
    private String productLabel;
    private int productType;

    private SchoolProduct parent;
    private Integer schoolId;
}
