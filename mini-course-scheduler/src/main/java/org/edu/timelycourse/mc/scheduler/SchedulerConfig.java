package org.edu.timelycourse.mc.scheduler;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by marco on 2018/5/12
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackageClasses = SchedulerConfig.class)
public class SchedulerConfig
{
}
