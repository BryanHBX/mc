package org.edu.timelycourse.mc.biz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.edu.timelycourse.mc.biz.enums.EUserRole;
import org.edu.timelycourse.mc.biz.enums.EUserType;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.edu.timelycourse.mc.common.utils.StringUtil;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Marco on 2018/3/31.
 */
@Data
@ToString(exclude = "id")
@JsonIgnoreProperties(value = { "id", "password" })
public class UserModel extends BaseEntity
{
    /**
     * 身份证
     */
    private String userIdentity;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 电话
     */
    private String phone;

    /**
     * 微信号
     */
    private String wxId;

    /**
     * 用户状态
     */
    private Integer status;

    /**
     * 学校ID
     */
    private Integer schoolId;

    /**
     * 用户类型, 机构 / 个人
     */
    private Integer type;

    /**
     * 用户角色, 机构管理员 / 普通用户 / 系统管理员
     */
    private Integer role;

    /**
     * 用户权限描述
     */
    private String roles;
    private List<Integer> roleIds;

    /**
     * 所授年级
     */
    private String gradesId;
    private String gradesDesc;
    private List<Integer> gradeIds;

    /**
     * 所授课程
     */
    private String coursesId;
    private String coursesDesc;
    private List<Integer> courseIds;

    /**
     * 所授科目
     */
    private String subjectsId;
    private String subjectsDesc;
    private List<Integer> subjectIds;

    private Date creationTime;
    private Date lastUpdateTime;
    private Date lastLoginTime;

    /**
     * 拥有权限
     */
    private List<UserRoleModel> authorities;

    public UserModel()
    {
    }

    public UserModel(String identity, String phone, String wxId)
    {
        this.userIdentity = identity;
        this.phone = phone;
        this.wxId = wxId;
    }

    public UserModel(UserModel user)
    {
        this(user, null);
    }

    public UserModel(UserModel user, UserRoleModel role)
    {
        this.userIdentity = user.getUserIdentity();

        if (role != null)
        {
            this.authorities.add(role);
        }
    }

    public void addRole (final UserRoleModel userRole)
    {
        if (userRole != null)
        {
            if (authorities == null)
            {
                authorities = new ArrayList<UserRoleModel>();
            }

            authorities.add(userRole);
        }
    }

    @Override
    public boolean isValidInput ()
    {
        return EUserRole.hasValue(this.status) &&
                EUserType.hasValue(this.type) && EUserRole.hasValue(this.role) &&
                EntityUtils.isValidEntityId(this.schoolId) && StringUtil.isNotEmpty(this.phone);
    }

    @JsonIgnore
    public String getUrlParams ()
    {
        StringBuilder builder = new StringBuilder();
        appendParam(builder, "userName", userName);
        appendParam(builder, "schoolId", schoolId);
        return builder.toString();
    }

    private void appendParam (StringBuilder builder, String paramName, Object paramValue)
    {
        if (paramValue != null)
        {
            builder.append(paramName + "=" + paramValue.toString() + "&");
        }
    }
}
