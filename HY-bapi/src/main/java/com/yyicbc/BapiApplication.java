package com.yyicbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class BapiApplication {
    public static void main(String[] args) {
        SpringApplication.run(BapiApplication.class, args);
    }

}
