package com.yyicbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableConfigurationProperties
@EnableEurekaClient
public class HYProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(HYProviderApplication.class, args);
    }

}



