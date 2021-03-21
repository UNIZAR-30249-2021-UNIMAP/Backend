package com.labis.unimap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class UnimapApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnimapApplication.class, args);
    }

}
