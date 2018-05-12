package org.edu.timelycourse.mc.beans.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.edu.timelycourse.mc.beans.enums.EUserRole;
import org.edu.timelycourse.mc.beans.enums.EUserType;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.edu.timelycourse.mc.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 平台用户
 *
 * Created by Marco on 2018/3/31.
 */
@Data
@ToString(exclude = "id")
@JsonIgnoreProperties(value = { "password" })
public class UserModel extends BaseModel
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
    @JsonIgnore
    private SchoolModel school;

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
    private String rolesDesc;
    private List<Integer> roleIds;

    /**
     * 所授年级
     */
    private String gradesId;
    private String gradesDesc;
    private List<Integer> gradeIds;
    public List<Integer> getGradeIds()
    {
        if (gradeIds == null || gradeIds.size() == 0)
        {
            gradeIds = fromJoinedString(gradesId);
        }
        return gradeIds;
    }

    /**
     * 所授课程
     */
    private String coursesId;
    private String coursesDesc;
    private List<Integer> courseIds;
    public List<Integer> getCourseIds()
    {
        if (courseIds == null || courseIds.size() == 0)
        {
            courseIds = fromJoinedString(coursesId);
        }
        return courseIds;
    }

    /**
     * 所授科目
     */
    private String subjectsId;
    private String subjectsDesc;
    private List<Integer> subjectIds;
    public List<Integer> getSubjectIds()
    {
        if (subjectIds == null || subjectIds.size() == 0)
        {
            subjectIds = fromJoinedString(subjectsId);
        }

        return subjectIds;
    }

    private List<Integer> fromJoinedString (String str)
    {
        List<Integer> result = new ArrayList<>();
        if (str != null)
        {
            String[] idSlices = str.split(",");
            for (String id : idSlices)
            {
                result.add(Integer.valueOf(id));
            }
        }
        return result;
    }

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
                EntityUtils.isValidEntityId(this.schoolId) &&
                StringUtils.isNotEmpty(this.phone);
    }
}
