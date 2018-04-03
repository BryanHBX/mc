package org.edu.energycourse.mc.biz.entity.school;

import lombok.Data;
import org.edu.energycourse.mc.biz.entity.BaseEntity;

@Data
public class SchoolInfo extends BaseEntity
{
    private String schoolName;
    private String schoolAddress;
    private String schoolContact;
    private int schoolStatus;
}
