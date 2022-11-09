package com.news.article.config;

import com.news.article.interceptor.AuInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuInterceptor()).addPathPatterns("/**").excludePathPatterns("/login/").excludePathPatterns("/register/");
    }
}
