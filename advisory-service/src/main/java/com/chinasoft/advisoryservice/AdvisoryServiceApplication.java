package com.chinasoft.advisoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AdvisoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdvisoryServiceApplication.class, args);
    }

}
