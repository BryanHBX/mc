package org.edu.timelycourse.mc.beans.model;

import lombok.Data;
import org.edu.timelycourse.mc.common.utils.StringUtil;

import java.util.Date;

@Data
public class SchoolModel extends BaseModel
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
     * 所在省份
     */
    private String province;

    /**
     * 所在城市
     */
    private String city;

    /**
     * 经度
     */
    private Double lat;

    /**
     * 纬度
     */
    private Double lng;

    /**
     * 学校状态
     */
    private Integer schoolStatus;

    /**
     * 创建时间
     */
    private Date creationTime;

    /**
     * 父校区ID
     */
    private Integer parentId;

    /**
     * 支持学管师
     */
    private Integer supervisorFlag;

    /**
     * 更新时间
     */
    private Date lastUpdateTime;

    public SchoolModel () {}

    public SchoolModel (String name, String address, String contact, Integer status)
    {
        this.schoolName = name;
        this.schoolAddress = address;
        this.schoolContact = contact;
        this.schoolStatus = status;
    }

    @Override
    public boolean isValidInput ()
    {
        return StringUtil.isNotEmpty(schoolName);
    }

    @Override
    public String getUrlParams()
    {
        return null;
    }
}
