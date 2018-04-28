package org.edu.timelycourse.mc.biz.repository;

import org.apache.ibatis.annotations.Param;
import org.edu.timelycourse.mc.beans.criteria.BaseCriteria;
import org.edu.timelycourse.mc.beans.model.ContractInvoiceModel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by x36zhao on 2018/3/31.
 */
@Component
public interface ContractInvoiceRepository extends BaseRepository<ContractInvoiceModel>
{

    Double getTotalFeeByCriteria(final BaseCriteria criteria);

    List<ContractInvoiceModel> getByContractId(@Param("cid") Integer contractId,
                                               @Param("sid") Integer schoolId);
}
