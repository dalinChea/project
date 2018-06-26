package com.example.demotopic03.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceHandlerConfiguration implements WebMvcConfigurer{

    @Value("/images-btb/")
    private String CLIENT_PATH;

    @Value("/btb/")
    private String SERVER_PATH;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler(CLIENT_PATH+"**").addResourceLocations("file:"+SERVER_PATH);
    }
}
