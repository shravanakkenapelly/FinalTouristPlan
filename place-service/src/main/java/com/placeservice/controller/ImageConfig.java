package com.placeservice.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImageConfig implements WebMvcConfigurer {

    private static final Logger logger = LogManager.getLogger(ImageConfig.class);

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        logger.info("Configuring resource handlers...");
        registry.addResourceHandler("/images/**").addResourceLocations("file:C:/Users/SHAIAHAM/Pictures/ProjectPlacePics/");
    }
}


