package com.dailycoding.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 取得專案根目錄下的 uploads 資料夾絕對路徑
        String uploadPath = Paths.get(System.getProperty("user.dir") + "/uploads").toUri().toString();

        // 將 /uploads/** 的請求映射到該資料夾
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadPath);
    }
}
