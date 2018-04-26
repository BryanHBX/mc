package org.edu.timelycourse.mc.biz.repository;

import org.apache.ibatis.annotations.Param;
import org.edu.timelycourse.mc.beans.model.ContractModel;
import org.springframework.stereotype.Component;

/**
 * Created by x36zhao on 2018/3/31.
 */
@Component
public interface ContractRepository extends BaseRepository<ContractModel>
{
    ContractModel getByContractNo(@Param("contractNo") String contractNo,
                                  @Param("sid") Integer schoolId);
}
