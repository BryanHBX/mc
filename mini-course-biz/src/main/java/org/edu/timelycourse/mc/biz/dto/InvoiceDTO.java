package org.edu.timelycourse.mc.biz.dto;

import lombok.Data;

import java.util.Date;

/**
 * Created by x36zhao on 2018/4/19.
 */
@Data
public class InvoiceDTO extends BaseDTO
{
    private String enrollType;
    private Date enrollDate;
}
