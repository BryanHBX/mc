package org.edu.timelycourse.mc.beans.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.edu.timelycourse.mc.beans.dto.ContractArrangementDTO;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.edu.timelycourse.mc.common.utils.StringUtil;
import org.springframework.beans.BeanUtils;

/**
 * Created by x36zhao on 2018/4/20.
 */
@Data
public class ContractArrangementModel extends BaseModel
{
    @JsonIgnore
    private Integer schoolId;
    private Integer contractId;
    private Integer teacherId;
    private Integer classId;
    private Integer absenceCost;

    @JsonIgnore
    private ContractClazzModel clazz;

    @JsonIgnore
    private UserModel teacher;

    @Override
    @JsonIgnore
    public boolean isValidInput ()
    {
        return EntityUtils.isValidEntityId(schoolId, contractId, teacherId) &&
                (classId == null || EntityUtils.isValidEntityId(classId));
    }

    public static ContractArrangementModel from (final ContractArrangementDTO dto)
    {
        ContractArrangementModel model = new ContractArrangementModel();
        BeanUtils.copyProperties(dto, model, "clazz");
        if (dto.getClazz() != null && StringUtil.isNotEmpty(dto.getClazz().getName()))
        {
            ContractClazzModel clazzModel = new ContractClazzModel();
            clazzModel.setId(dto.getClazz().getId());
            clazzModel.setName(dto.getClazz().getName());
            clazzModel.setTeacherId(dto.getTeacher().getId());
            clazzModel.setSchoolId(dto.getSchoolId());
            model.setClazz(clazzModel);
        }
        model.setTeacherId(dto.getTeacher().getId());
        model.setAbsenceCost(dto.getAbsenceCost() != null ? 1 : 0);
        return model;
    }
}
