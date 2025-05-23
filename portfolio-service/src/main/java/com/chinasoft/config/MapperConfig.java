package com.wealth.portfolioservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @author:张宇阳Alan
 * @date:2025/5/22
 * @description:Spring配置类，定义和创建ModelMapper bean
 */
@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
