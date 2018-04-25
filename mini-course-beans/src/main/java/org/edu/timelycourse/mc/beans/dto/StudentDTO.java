package org.edu.timelycourse.mc.beans.dto;

import lombok.Data;
import org.edu.timelycourse.mc.beans.enums.EContactType;
import org.edu.timelycourse.mc.beans.enums.EEnrollmentType;
import org.edu.timelycourse.mc.beans.enums.EStudentStatus;
import org.edu.timelycourse.mc.beans.model.ContractModel;
import org.edu.timelycourse.mc.beans.model.StudentModel;
import org.edu.timelycourse.mc.beans.paging.PagingBean;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by x36zhao on 2018/4/19.
 */
@Data
public class StudentDTO extends BaseDTO
{
    /**
     * 学生类型
     */
    private NamedOptionProperty studentStatus;

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
    private NamedOptionProperty contactRelation;

    /**
     * 学生年段
     */
    private NamedOptionProperty grade;

    /**
     * 细分年段
     */
    private NamedOptionProperty gradeSub;

    /**
     * 课程名称
     */
    private NamedOptionProperty course;

    /**
     * 课程子类
     */
    private NamedOptionProperty courseSub;

    /**
     * 咨询师
     */
    private NamedOptionProperty consultant;

    /**
     * 学管师
     */
    private NamedOptionProperty supervisor;

    public static List<StudentDTO> from (List<StudentModel> models)
    {
        List<StudentDTO> vos = new ArrayList<>();
        for (StudentModel model : models)
        {
            vos.add(from(model));
        }
        return vos;
    }

    public static PagingBean<StudentDTO> from (PagingBean<StudentModel> pagingBean)
    {
        PagingBean<StudentDTO> result = new PagingBean<>();
        result.setItems(from(pagingBean.getItems()));
        result.setPageNumber(pagingBean.getPageNumber());
        result.setPageSize(pagingBean.getPageSize());
        result.setTotalItems(pagingBean.getTotalItems());
        result.setTotalPageNumber(pagingBean.getTotalPageNumber());
        return result;
    }

    public static StudentDTO from (StudentModel model)
    {
        try
        {
            if (model != null)
            {
                StudentDTO dto = new StudentDTO();
                BeanUtils.copyProperties(model, dto, "course", "consultant", "supervisor");
                dto.setConsultant(NamedOptionProperty.from(model.getConsultantId(), model.getConsultant(), "userName"));
                dto.setGrade(NamedOptionProperty.from(model.getLevelId(), model.getLevel(), "configDescription"));
                dto.setGradeSub(NamedOptionProperty.from(model.getSubLevelId(), model.getSubLevel(), "configDescription"));
                dto.setCourse(NamedOptionProperty.from(model.getCourseId(), model.getCourse(), "productName"));
                dto.setCourseSub(NamedOptionProperty.from(model.getSubCourseId(), model.getSubCourse(), "productName"));
                dto.setSupervisor(NamedOptionProperty.from(model.getSupervisorId(), model.getSupervisor(), "userName"));
                dto.setContactRelation(new NamedOptionProperty(model.getContactRelationType(), EContactType.getLabel(model.getContactRelationType())));
                dto.setStudentStatus(new NamedOptionProperty(model.getStatus(), EStudentStatus.getLabel(model.getStatus())));
                return dto;
            }

            return null;
        }
        catch (Exception ex)
        {
            throw new RuntimeException(String.format(
                    "Failed to copy properties from student (%s) to DTO object", model
            ), ex);
        }
    }

    @Override
    public boolean isValid ()
    {
        return EntityUtils.isValidEntityId(getSchoolId());
    }
}
