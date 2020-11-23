package com.yyicbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;


@EnableConfigServer
@SpringBootApplication
public class HYMyconfigsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HYMyconfigsApplication.class, args);
    }

}
