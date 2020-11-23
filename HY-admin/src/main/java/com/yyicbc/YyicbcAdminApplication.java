package com.yyicbc;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@EnableDiscoveryClient
@EnableAdminServer
@SpringBootApplication
public class HYAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(HYAdminApplication.class, args);
    }

}
