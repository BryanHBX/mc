package org.edu.timelycourse.mc.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by x36zhao on 2018/3/31.
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackageClasses = BizConfig.class)
public class BizConfig
{
}
