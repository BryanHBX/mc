package org.edu.timelycourse.mc.biz.service;

import org.edu.timelycourse.mc.beans.model.ContractClassModel;
import org.edu.timelycourse.mc.biz.repository.ContractClassRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by x36zhao on 2017/3/17.
 */
@Service
public class ContractClassService extends BaseService<ContractClassModel>
{
    private static Logger LOGGER = LoggerFactory.getLogger(ContractClassService.class);

    @Autowired
    public ContractClassService (ContractClassRepository repository)
    {
        super(repository);
    }
}
