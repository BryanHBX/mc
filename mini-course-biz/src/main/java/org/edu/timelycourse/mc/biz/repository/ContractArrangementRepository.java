package org.edu.timelycourse.mc.biz.repository;

import org.apache.ibatis.annotations.Param;
import org.edu.timelycourse.mc.beans.model.ContractArrangementModel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by x36zhao on 2018/3/31.
 */
@Component
public interface ContractArrangementRepository extends BaseRepository<ContractArrangementModel>
{
    List<ContractArrangementModel> getByContract (@Param("cid") Integer contractId,
                                                  @Param("sid") Integer schoolId);

    List<ContractArrangementModel> getByTeacher (@Param("tid") Integer teacherId,
                                                 @Param("sid") Integer schoolId);

}
