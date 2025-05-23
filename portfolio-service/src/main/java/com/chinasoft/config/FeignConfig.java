package com.wealth.portfolioservice.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @author:张宇阳Alan
 * @date:2025/5/22
 * @description:Feign客户端配置类
 */
@Configuration
public class FeignConfig {
    
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}    