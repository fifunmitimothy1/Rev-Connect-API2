package com.rev_connect_api.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Disables cors I believe? Probably not recommended for production but ok for local dev
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Makes the resources/public directory accessible for images and videos
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/public/");
    }
}
