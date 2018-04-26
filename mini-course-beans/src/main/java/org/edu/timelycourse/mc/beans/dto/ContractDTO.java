package org.edu.timelycourse.mc.beans.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.edu.timelycourse.mc.beans.enums.EEnrollmentType;
import org.edu.timelycourse.mc.beans.model.ContractModel;
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
public class ContractDTO extends BaseDTO
{
    /**
     * 报名类型
     */
    private NamedOptionProperty enrollType;

    /**
     * 签约日期
     */
    private Date contractDate;

    /**
     * 合同创建时间
     */
    private Date creationTime;

    /**
     * 合同编号
     */
    private String contractNo;

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
     * 报名课时
     */
    private double enrollPeriod;

    /**
     * 赠送课时
     */
    private double freePeriod;

    /**
     * 转退课时
     */
    private double transferPeriod;

    /**
     * 剩余课时
     */
    private double remainedPeriod;

    /**
     * 签约总价
     */
    private double contractPrice;

    /**
     * 其他费用
     */
    private double otherPrice;

    /**
     * 优惠金额
     */
    private double discountPrice;

    /**
     * 实收金额
     */
    private double totalPrice;

    /**
     * 咨询师
     */
    private NamedOptionProperty consultant;

    /**
     * 学管师
     */
    private NamedOptionProperty supervisor;

    /**
     * 支付金额
     */
    private double paid;

    /**
     * 学生
     */
    private StudentDTO student;

    /**
     * 收据
     */
    private List<InvoiceDTO> invoices;

    /**
     * 合同状态
     */
    private Integer contractStatus;

    /**
     * 缴费状态
     */
    private Integer payStatus;

    /**
     * 剩余金额
     * @return
     */
    public double getRemainedPrice()
    {
        return ((totalPrice - otherPrice) / enrollPeriod) * remainedPeriod;
    }

    public static List<ContractDTO> from (List<ContractModel> models)
    {
        List<ContractDTO> vos = new ArrayList<>();
        for (ContractModel model : models)
        {
            vos.add(from(model));
        }
        return vos;
    }

    public static PagingBean<ContractDTO> from (PagingBean<ContractModel> pagingBean)
    {
        PagingBean<ContractDTO> result = new PagingBean<>();
        result.setItems(from(pagingBean.getItems()));
        result.setPageNumber(pagingBean.getPageNumber());
        result.setPageSize(pagingBean.getPageSize());
        result.setTotalItems(pagingBean.getTotalItems());
        result.setTotalPageNumber(pagingBean.getTotalPageNumber());
        return result;
    }

    public static ContractDTO from (ContractModel model)
    {
        try
        {
            if (model != null)
            {
                ContractDTO dto = new ContractDTO();
                BeanUtils.copyProperties(model, dto, "course", "student", "enrollType", "consultant", "supervisor");
                dto.setEnrollType(new NamedOptionProperty(model.getEnrollType(), EEnrollmentType.getLabel(model.getEnrollType())));
                dto.setConsultant(NamedOptionProperty.from(model.getConsultantId(), model.getConsultant(), "userName"));
                dto.setGrade(NamedOptionProperty.from(model.getLevelId(), model.getLevel(), "configDescription"));
                dto.setGradeSub(NamedOptionProperty.from(model.getSubLevelId(), model.getSubLevel(), "configDescription"));
                dto.setCourse(NamedOptionProperty.from(model.getCourseId(), model.getCourse(), "productName"));
                dto.setCourseSub(NamedOptionProperty.from(model.getSubCourseId(), model.getSubCourse(), "productName"));
                dto.setStudent(StudentDTO.from(model.getStudent()));
                dto.setSupervisor(NamedOptionProperty.from(model.getSupervisorId(), model.getSupervisor(), "userName"));
                dto.setInvoices(InvoiceDTO.from(model.getInvoices()));
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

    @Override
    @JsonIgnore
    public boolean isValid ()
    {
        return EntityUtils.isValidEntityId(getSchoolId());
    }
}
