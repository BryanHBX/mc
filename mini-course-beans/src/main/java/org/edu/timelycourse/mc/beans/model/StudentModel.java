package org.edu.timelycourse.mc.beans.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.edu.timelycourse.mc.beans.dto.ContractDTO;
import org.edu.timelycourse.mc.beans.dto.StudentDTO;
import org.edu.timelycourse.mc.beans.enums.EContactType;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.edu.timelycourse.mc.common.utils.StringUtil;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
public class StudentModel extends BaseModel
{
    /**
     * 学生姓名
     */
    private String name;

    /**
     * 微信号
     */
    private String wxId;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人电话
     */
    private String contactPhone;

    /**
     * 其他备注
     */
    private String meto;

    /**
     * 联系人关系
     */
    private Integer contactRelationType;

    /**
     * 咨询师
     */
    private Integer consultantId;

    @JsonIgnore
    private UserModel consultant;

    /**
     * 学管师
     */
    private Integer supervisorId;

    @JsonIgnore
    private UserModel supervisor;

    /**
     * 学生年段
     */
    private Integer levelId;

    @JsonIgnore
    private SystemConfigModel level;

    /**
     * 细分年段
     */
    private Integer subLevelId;

    @JsonIgnore
    private SystemConfigModel subLevel;

    /**
     * 课程名称
     */
    private Integer courseId;

    @JsonIgnore
    private SchoolProductModel course;

    /**
     * 课程子类
     */
    private Integer subCourseId;

    @JsonIgnore
    private SchoolProductModel subCourse;

    /**
     * 创建时间
     */
    private Date creationTime;

    /**
     * 更新时间
     */
    private Date lastUpdateTime;

    /**
     * 学校ID
     */
    private Integer schoolId;

    /**
     * 学生状态
     */
    private Integer status;

    @Override
    public boolean isValidInput ()
    {
        return EContactType.hasValue(this.contactRelationType) &&
                StringUtil.isNotEmpty(wxId, contactName, contactPhone, name) &&
                EntityUtils.isValidEntityId(schoolId, levelId, subLevelId, courseId, subCourseId);
    }

    public static StudentModel from (final StudentDTO dto)
    {
        StudentModel model = new StudentModel();
        BeanUtils.copyProperties(dto, model, "course", "consultant", "supervisor");
        model.setConsultantId(dto.getConsultant() != null ? dto.getConsultant().getId() : null);
        model.setSupervisorId(dto.getSupervisor() != null ? dto.getSupervisor().getId() : null);
        model.setCourseId(dto.getCourse() != null ? dto.getCourse().getId() : null);
        model.setSubCourseId(dto.getCourseSub() != null ? dto.getCourseSub().getId() : null);
        model.setLevelId(dto.getGrade() != null ? dto.getGrade().getId() : null);
        model.setSubLevelId(dto.getGradeSub() != null ? dto.getGradeSub().getId() : null);
        model.setContactRelationType(dto.getContactRelation() != null ? dto.getContactRelation().getId() : null);
        model.setStatus(dto.getStudentStatus() != null ? dto.getStudentStatus().getId() : null);
        return model;
    }
}
