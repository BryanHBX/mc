package org.edu.energycourse.mc.api;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Marco on 2018/3/31
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackageClasses = Application.class)
public class Application
{
}
