package com.news.admin.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {"com.cl.common.exception","com.cl.common.knife4j"})
public class InitConfig {
}
