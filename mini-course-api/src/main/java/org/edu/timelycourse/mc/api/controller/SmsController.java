package org.edu.timelycourse.mc.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.edu.timelycourse.mc.beans.entity.ResponseData;
import org.edu.timelycourse.mc.common.utils.CommonUtils;
import org.edu.timelycourse.mc.common.utils.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by marco on 2018/4/29
 */
@RestController
@RequestMapping("/api/${api.version}/sms")
@Api(tags = { "短消息API" })
public class SmsController extends BaseController
{
    private static final String SECRET_KEY = "mini-course";
    private static Logger LOGGER = LoggerFactory.getLogger(SmsController.class);

    @RequestMapping(path="/sendMsg", method= RequestMethod.GET)
    @ApiOperation(value = "Send short message")
    public ResponseData sendMessage()
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Enter sendMessage - []");
        }

        //生成随机数
        String randomNum = CommonUtils.createRandomNum(6);
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");

        // 生成5分钟后时间，用户校验是否过期
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, 5);
        String currentTime = sf.format(c.getTime());

        //生成MD5值,将hash值和tamp时间返回给前端
        String hash = MD5Utils.getMD5Code(SECRET_KEY + "@" + currentTime + "@" + randomNum);
        return null;
    }
}
