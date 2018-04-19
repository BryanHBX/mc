package org.edu.timelycourse.mc.biz.dto;

import lombok.Data;
import org.edu.timelycourse.mc.biz.enums.EEnrollmentType;
import org.edu.timelycourse.mc.biz.model.ContractModel;
import org.edu.timelycourse.mc.biz.model.InvoiceModel;
import org.edu.timelycourse.mc.biz.paging.PagingBean;
import org.edu.timelycourse.mc.biz.utils.OptionProperty;
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
    private OptionProperty<Integer, String> typeOpt;

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
     *学生姓名
     */
    private String studentName;

    /**
     * 学生年段
     */
    private OptionProperty<Integer, String> gradeOpt;

    /**
     * 细分年段
     */
    private OptionProperty<Integer, String> gradeSubLevelOpt;

    /**
     * 课程名称
     */
    private OptionProperty<Integer, String> courseOpt;

    /**
     * 课程子类
     */
    private OptionProperty<Integer, String> courseSubLevelOpt;

    /**
     * 报名课时
     */
    private double enrollPeriod;

    /**
     * 赠送课时
     */
    private double freePeriod;

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
     * 咨询师
     */
    private OptionProperty<Integer, String> consultantOpt;

    /**
     * 学管师
     */
    private OptionProperty<Integer, String> supervisorOpt;

    /**
     * 合同ID
     */
    private Integer id;

    /**
     * 学校ID
     */
    private Integer schoolId;

    /**
     * 支付金额
     */
    private double paid;

    /**
     * 学生
     */
    private OptionProperty<Integer, String> studentOpt;

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
            ContractDTO vo = new ContractDTO();
            BeanUtils.copyProperties(model, vo);
            vo.setTypeOpt(new OptionProperty<>(model.getEnrollType(), EEnrollmentType.getLabel(model.getEnrollType())));
            vo.setConsultantOpt(OptionProperty.from(model.getConsultantId(), model.getConsultant(), "userName"));
            vo.setGradeOpt(OptionProperty.from(model.getLevelId(), model.getLevel(), "configDescription"));
            vo.setGradeSubLevelOpt(OptionProperty.from(model.getSubLevelId(), model.getSubLevel(), "configDescription"));
            vo.setCourseOpt(OptionProperty.from(model.getCourseId(), model.getCourse(), "productName"));
            vo.setCourseSubLevelOpt(OptionProperty.from(model.getSubCourseId(), model.getSubCourse(), "productName"));
            vo.setStudentOpt(OptionProperty.from(model.getStudentId(), model.getStudent(), "name"));
            vo.setSupervisorOpt(OptionProperty.from(model.getSupervisorId(), model.getSupervisor(), "userName"));
            vo.setPaid(model.getPayTotal());
            return vo;
        }
        catch (Exception ex)
        {
            throw new RuntimeException(String.format(
                    "Failed to copy properties from contract (%s) to VO object", model
            ), ex);
        }
    }
}
