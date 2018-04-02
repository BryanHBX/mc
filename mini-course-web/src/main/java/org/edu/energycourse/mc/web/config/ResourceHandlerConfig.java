package org.edu.energycourse.mc.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by x36zhao on 2018/3/31.
 */
@Configuration
public class ResourceHandlerConfig extends WebMvcConfigurerAdapter
{
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        //registry.addResourceHandler("/assets/**")
        //        .addResourceLocations("classpath:/resources/static/");

        super.addResourceHandlers(registry);
    }
}
