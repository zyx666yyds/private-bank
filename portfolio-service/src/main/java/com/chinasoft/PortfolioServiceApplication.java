package com.wealth.portfolioservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
/**
 * @author:张宇阳Alan
 * @date:2025/5/22
 * @description:应用启动类
 */
@SpringBootApplication  //启用springboot
@EnableEurekaClient //启用eureka客户端
@EnableJpaAuditing  //启用jpa审计
public class PortfolioServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PortfolioServiceApplication.class, args);
    }
}    