package org.edu.timelycourse.mc.biz.repository;

import org.apache.ibatis.annotations.Param;
import org.edu.timelycourse.mc.biz.model.InvoiceModel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by x36zhao on 2018/3/31.
 */
@Component
public interface InvoiceRepository extends BaseRepository<InvoiceModel>
{
    List<InvoiceModel> getByContractId(@Param("contractId") Integer contractId);
}
