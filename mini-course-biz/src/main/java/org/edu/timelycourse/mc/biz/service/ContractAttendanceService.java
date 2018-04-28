package org.edu.timelycourse.mc.biz.service;

import org.edu.timelycourse.mc.beans.model.ContractAttendanceModel;
import org.edu.timelycourse.mc.biz.repository.ContractAttendanceRepository;
import org.edu.timelycourse.mc.biz.utils.Asserts;
import org.edu.timelycourse.mc.biz.utils.SecurityContextHelper;
import org.edu.timelycourse.mc.common.exception.ServiceException;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by x36zhao on 2017/3/17.
 */
@Service
public class ContractAttendanceService extends BaseService<ContractAttendanceModel>
{
    private static Logger LOGGER = LoggerFactory.getLogger(ContractAttendanceService.class);

    @Autowired
    public ContractAttendanceService (ContractAttendanceRepository repository)
    {
        super(repository);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ContractAttendanceModel add (ContractAttendanceModel model)
    {
        model.setSchoolId(SecurityContextHelper.getSchoolIdFromPrincipal());
        if (model.isValidInput())
        {
            return null;
        }
        throw new ServiceException(String.format("Invalid model data to add, %s", model));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ContractAttendanceModel update (ContractAttendanceModel model)
    {
        model.setSchoolId(SecurityContextHelper.getSchoolIdFromPrincipal());
        if (model.isValidInput() && EntityUtils.isValidEntityId(model.getId()))
        {
            // check if entity exists
            ContractAttendanceModel entityInDb = (ContractAttendanceModel) Asserts.assertEntityNotNullById(
                    repository, model.getId());

            // check if any change with respect to contract

            return null;
        }
        throw new ServiceException(String.format("Invalid model data to add, %s", model));
    }
}
