package org.edu.timelycourse.mc.beans.model;

import lombok.Data;
import org.apache.logging.log4j.util.Strings;
import org.edu.timelycourse.mc.beans.dto.ContractDTO;
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
    private UserModel consultant;

    /**
     * 学管师
     */
    private Integer supervisorId;
    private UserModel supervisor;

    /**
     * 报名类型
     */
    private Integer enrollType;

    /**
     * 学生ID
     */
    private Integer studentId;

    /**
     * 学生信息
     */
    private StudentModel student;

    /**
     * 学生年段
     */
    private Integer levelId;
    private SystemConfigModel level;

    /**
     * 细分年段
     */
    private Integer subLevelId;
    private SystemConfigModel subLevel;

    /**
     * 课程名称
     */
    private Integer courseId;
    private SchoolProductModel course;

    /**
     * 课程子类
     */
    private Integer subCourseId;
    private SchoolProductModel subCourse;

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
     * 签约总价
     */
    private double contractPrice;

    /**
     * 优惠金额
     */
    private double discountPrice;

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
    private Date creationTime;

    /**
     * 合同更新时间
     */
    private Date lastUpdateTime;

    /**
     * 收据列表
     */
    private List<ContractInvoiceModel> invoices;

    /**
     * 合同状态
     */
    private Integer contractStatus;

    /**
     * 缴费状态
     */
    private Integer payStatus;

    /**
     * 获取已缴费金额
     */
    public double getPayTotal ()
    {
        double total = 0;
        if (invoices != null)
        {
            for (ContractInvoiceModel invoice : invoices)
            {
                total += invoice.getPrice();
            }
        }
        return total;
    }

    /**
     * 学校ID
     */
    private Integer schoolId;

    public ContractModel () {}

    public ContractModel (String contractNo, Integer schoolId)
    {
        this.contractNo = contractNo;
        this.schoolId = schoolId;
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

        return valid;
    }

    public static ContractModel from (final ContractDTO dto)
    {
        ContractModel model = new ContractModel();
        BeanUtils.copyProperties(dto, model, "course", "student", "enrollType", "consultant", "supervisor");
        model.setStudentId(dto.getStudent() != null ? dto.getStudent().getId() : null);
        model.setConsultantId(dto.getConsultant() != null ? dto.getConsultant().getId() : null);
        model.setSupervisorId(dto.getSupervisor() != null ? dto.getSupervisor().getId() : null);
        return model;
    }
}
