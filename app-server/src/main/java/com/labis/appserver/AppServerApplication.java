package com.labis.appserver;

import com.labis.appserver.service.IncidenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppServerApplication {

    @Autowired
    private IncidenciaService servicioPrueba;

    @Bean
    public CommandLineRunner runner() {
        return (args) -> {
            servicioPrueba.Test();
        };
    }

    public static void main(String[] args) {

        SpringApplication.run(AppServerApplication.class, args);
    }

}
