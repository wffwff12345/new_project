package com.news.appuser.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {"com.cl.common.exception","com.cl.common.knife4j","com.cl.common.aliyun"})
public class InitConfig {
}