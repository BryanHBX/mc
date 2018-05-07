package org.edu.timelycourse.mc.beans.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.edu.timelycourse.mc.beans.enums.EContractArrangementStatus;
import org.edu.timelycourse.mc.beans.enums.EContractStatus;
import org.edu.timelycourse.mc.beans.enums.EEnrollmentType;
import org.edu.timelycourse.mc.beans.model.ContractArrangementModel;
import org.edu.timelycourse.mc.beans.model.ContractModel;
import org.edu.timelycourse.mc.beans.paging.PagingBean;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.edu.timelycourse.mc.common.utils.StringUtil;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco on 2018/4/27
 */
@Data
@JsonIgnoreProperties(value = { "schoolId" })
public class ContractArrangementDTO extends BaseDTO
{
    public static String TYPE_TEACHER = "teacher";
    public static String TYPE_SUPERVISOR = "supervisor";

    private Integer id;
    private NamedOptionProperty teacher;
    private NamedOptionProperty clazz;

    @JsonIgnore
    private Integer contractId;

    private Integer absenceCost;
    private String operationType;

    @Override
    @JsonIgnore
    public boolean isValid()
    {
        return teacher != null && EntityUtils.isValidEntityId(teacher.getId(), contractId) &&
                (operationType.equalsIgnoreCase(TYPE_SUPERVISOR) || operationType.equalsIgnoreCase(TYPE_TEACHER));
    }

    public static List<ContractArrangementDTO> from (List<ContractArrangementModel> models)
    {
        List<ContractArrangementDTO> results = new ArrayList<>();
        if (models != null)
        {
            for (ContractArrangementModel model : models)
            {
                results.add(from(model));
            }
        }
        return results;
    }

    public static ContractArrangementDTO from (ContractArrangementModel model)
    {
        try
        {
            if (model != null)
            {
                ContractArrangementDTO dto = new ContractArrangementDTO();
                BeanUtils.copyProperties(model, dto);
                dto.setTeacher(NamedOptionProperty.from(model.getTeacherId(), model.getTeacher(), "userName"));
                dto.setClazz(NamedOptionProperty.from(model.getClassId(), model.getClazz(), "name"));
                return dto;
            }

            return null;
        }
        catch (Exception ex)
        {
            throw new RuntimeException(String.format(
                    "Failed to copy properties from contract (%s) to VO object", model
            ), ex);
        }
    }
}
