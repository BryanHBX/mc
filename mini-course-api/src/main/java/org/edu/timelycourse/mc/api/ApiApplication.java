package org.edu.timelycourse.mc.api;

import org.edu.timelycourse.mc.biz.BizConfig;
import org.edu.timelycourse.mc.common.CommonConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Marco on 2018/3/31.
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = { BizConfig.class, CommonConfig.class, ApiApplication.class })
@MapperScan(basePackages = {"org.edu.timelycourse.mc.biz.repository"})
public class ApiApplication extends SpringBootServletInitializer
{
    public static void main(String[] args)
    {
        SpringApplication.run(ApiApplication.class, args);
    }
}

