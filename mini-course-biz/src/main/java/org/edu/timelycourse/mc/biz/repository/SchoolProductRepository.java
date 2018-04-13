package org.edu.timelycourse.mc.biz.repository;

import org.apache.ibatis.annotations.Param;
import org.edu.timelycourse.mc.biz.model.SchoolProductModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SchoolProductRepository extends BaseRepository<SchoolProductModel>
{
    SchoolProductModel getByProductName (@Param("name") String productName,
                                  @Param("parentId") Integer parentId);

    List<SchoolProductModel> getChildren (@Param("parentId") Integer parentId);
}
