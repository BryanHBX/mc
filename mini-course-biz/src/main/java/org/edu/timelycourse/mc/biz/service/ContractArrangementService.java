package org.edu.timelycourse.mc.biz.service;

import org.edu.timelycourse.mc.beans.model.ContractAttendanceModel;
import org.edu.timelycourse.mc.biz.repository.ContractAttendanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by x36zhao on 2017/3/17.
 */
@Service
public class ContractArrangementService extends BaseService<ContractAttendanceModel>
{
    private static Logger LOGGER = LoggerFactory.getLogger(ContractArrangementService.class);

    @Autowired
    public ContractArrangementService (ContractAttendanceRepository repository)
    {
        super(repository);
    }
}
