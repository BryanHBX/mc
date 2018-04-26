package org.edu.timelycourse.mc.beans.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;
import org.edu.timelycourse.mc.beans.dto.ContractDTO;
import org.edu.timelycourse.mc.beans.dto.InvoiceDTO;
import org.edu.timelycourse.mc.beans.enums.EContractDebtStatus;
import org.edu.timelycourse.mc.beans.enums.EContractStatus;
import org.edu.timelycourse.mc.beans.enums.EEnrollmentType;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.edu.timelycourse.mc.common.utils.ValidatorUtil;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

@Data
public class ContractModel extends BaseModel
{
    /**
     * 合同编号
     */
    private String contractNo;

    /**
     * 咨询师ID
     */
    private Integer consultantId;

    /**
     * 学管师
     */
    private Integer supervisorId;

    /**
     * 报名类型
     */
    private Integer enrollType;

    /**
     * 学生ID
     */
    private Integer studentId;

    /**
     * 学生年段
     */
    private Integer levelId;

    /**
     * 细分年段
     */
    private Integer subLevelId;

    /**
     * 课程名称
     */
    private Integer courseId;

    /**
     * 课程子类
     */
    private Integer subCourseId;

    /**
     * 报名课时
     */
    private double enrollPeriod;

    /**
     * 赠送课时
     */
    private double freePeriod;

    /**
     * 剩余课时
     */
    private double remainedPeriod;

    /**
     * 转退课时
     */
    private double transferPeriod;

    /**
     * 签约总价
     */
    private double contractPrice;

    /**
     * 优惠金额
     */
    private double discountPrice;

    /**
     * 其他费用
     */
    private double otherPrice;

    /**
     * 实收金额
     */
    private double totalPrice;

    /**
     * 合同签约日期
     */
    private Date contractDate;

    /**
     * 合同创建时间
     */
    @JsonIgnore
    private Date creationTime;

    /**
     * 合同更新时间
     */
    @JsonIgnore
    private Date lastUpdateTime;

    /**
     * 已支付金额
     */
    private double paid;

    /**
     * 合同状态
     */
    private Integer contractStatus;

    /**
     * 缴费状态
     */
    private Integer payStatus;

    /**
     * 学校ID
     */
    @JsonIgnore
    private Integer schoolId;

    @JsonIgnore
    private SystemConfigModel subLevel;

    @JsonIgnore
    private SystemConfigModel level;

    @JsonIgnore
    private StudentModel student;

    @JsonIgnore
    private UserModel consultant;

    @JsonIgnore
    private UserModel supervisor;

    @JsonIgnore
    private SchoolProductModel course;

    @JsonIgnore
    private SchoolProductModel subCourse;

    /**
     * 收据列表
     */
    @JsonIgnore
    private List<ContractInvoiceModel> invoices;

    public ContractModel () {}

    public ContractModel (String contractNo, Integer schoolId)
    {
        this.contractNo = contractNo;
        this.schoolId = schoolId;
    }

    /**
     * 剩余金额
     * @return
     */
    public double getRemainedPrice()
    {
        return ((totalPrice - otherPrice) / enrollPeriod) * remainedPeriod;
    }

    /**
     * 获取课时单价
     * @return
     */
    public double getPricePerPeriod ()
    {
        return (totalPrice - otherPrice) / enrollPeriod;
    }

    /**
     * 获取缴费总额
     * @return
     */
    public double getInvoicePayTotal ()
    {
        double payTotal = 0;
        if (invoices != null)
        {
            for (ContractInvoiceModel invoice : invoices)
            {
                payTotal += invoice.getPrice();
            }
        }
        return payTotal;
    }

    @Override
    public boolean isValidInput ()
    {
        boolean valid =
                EEnrollmentType.hasValue(enrollType) && EContractStatus.hasValue(contractStatus) &&
                Strings.isNotEmpty(contractNo) && EContractDebtStatus.hasValue(payStatus) &&
                ValidatorUtil.isFloatNumber(contractPrice, totalPrice) &&
                EntityUtils.isValidEntityId(consultantId, levelId, subLevelId, courseId, subCourseId, schoolId) &&
                student.isValidInput() && contractDate != null;

        if (valid && invoices != null)
        {
            for (ContractInvoiceModel invoice : invoices)
            {
                invoice.setSchoolId(schoolId);
                valid = invoice.isValidInput();
                if (!valid)
                {
                    break;
                }
            }
        }

        if (getInvoicePayTotal() > 0)
        {
            valid = getInvoicePayTotal() <= totalPrice;
        }

        return valid;
    }

    public static ContractModel from (final ContractDTO dto)
    {
        ContractModel model = new ContractModel();
        BeanUtils.copyProperties(dto, model, "course", "student", "enrollType", "consultant", "supervisor");
        if (dto.getStudent() != null)
        {
            model.setStudentId(dto.getStudent().getId());
            model.setStudent(StudentModel.from(dto.getStudent()));
        }

        if (dto.getStatus() != null)
        {
            model.setContractStatus(dto.getStatus().getId());
        }

        model.setConsultantId(dto.getConsultant() != null ? dto.getConsultant().getId() : null);
        model.setSupervisorId(dto.getSupervisor() != null ? dto.getSupervisor().getId() : null);
        model.setEnrollType(dto.getEnrollType() != null ? dto.getEnrollType().getId() : null);
        model.setLevelId(dto.getGrade() != null ? dto.getGrade().getId() : null);
        model.setSubLevelId(dto.getGradeSub() != null ? dto.getGradeSub().getId() : null);
        model.setCourseId(dto.getCourse() != null ? dto.getCourse().getId() : null);
        model.setSubCourseId(dto.getCourseSub() != null ? dto.getCourseSub().getId() : null);

        if (dto.getInvoices() != null)
        {
            model.setInvoices(ContractInvoiceModel.from(dto.getInvoices()));
        }

        return model;
    }
}
