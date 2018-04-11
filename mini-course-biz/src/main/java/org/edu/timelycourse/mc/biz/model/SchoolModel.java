package org.edu.timelycourse.mc.biz.model;

import lombok.Data;
import org.edu.timelycourse.mc.biz.model.BaseEntity;

import java.util.Date;

@Data
public class SchoolModel extends BaseEntity
{
    /**
     * 学校名
     */
    private String schoolName;

    /**
     * 学校地址
     */
    private String schoolAddress;

    /**
     * 学校联系方式
     */
    private String schoolContact;

    /**
     * 学校状态
     */
    private int schoolStatus;

    /**
     * 创建时间
     */
    private Date creationTime;

    /**
     * 更新时间
     */
    private Date lastUpdateTime;

    @Override
    public boolean isValidInput ()
    {
        return true;
    }
}
