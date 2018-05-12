package org.edu.timelycourse.mc.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Created by Marco on 2018/3/31.
 */
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
    }
)
//@ComponentScan(basePackageClasses = { WebApplication.class, ExportConfig.class })
public class WebApplication extends SpringBootServletInitializer
{

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(WebApplication.class);
    }

//    @Bean
//    public FilterRegistrationBean gzipFilter()
//    {
//        FilterRegistrationBean filterBean = new FilterRegistrationBean();
//        filterBean.setFilter(new GZIPFilter());
//        return filterBean;
//    }

    public static void main(String[] args)
    {
        SpringApplication.run(WebApplication.class, args);
    }
}
