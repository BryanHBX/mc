package org.edu.timelycourse.mc.biz.entity.school;

import lombok.Data;
import org.edu.timelycourse.mc.biz.entity.BaseEntity;

@Data
public class SchoolBasicInfo extends BaseEntity
{
    private String schoolName;
    private String schoolAddress;
    private String schoolContact;
    private int schoolStatus;
}
