package com.yyicbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;


@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
//@EnableEurekaClient
//@EnableHystrix
@EnableDiscoveryClient
public class HYConsumer1Application {

    public static void main(String[] args) {
        SpringApplication.run(HYConsumer1Application.class, args);
    }

}
