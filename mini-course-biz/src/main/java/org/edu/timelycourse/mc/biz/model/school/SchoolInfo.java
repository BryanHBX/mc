package org.edu.timelycourse.mc.biz.model.school;

import lombok.Data;
import org.edu.timelycourse.mc.biz.model.BaseEntity;

import java.util.Date;

@Data
public class SchoolInfo extends BaseEntity
{
    private String schoolName;
    private String schoolAddress;
    private String schoolContact;
    private int schoolStatus;
    private Date creationTime;
    private Date lastUpdateTime;
}
