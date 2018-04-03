package org.edu.energycourse.mc.api;

import org.edu.energycourse.mc.biz.BizConfig;
import org.edu.energycourse.mc.common.CommonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Marco on 2018/3/31.
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = { BizConfig.class, CommonConfig.class })
public class Application extends SpringBootServletInitializer
{
    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }
}

