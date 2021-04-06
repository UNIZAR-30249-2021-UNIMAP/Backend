package com.labis.appserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class AppServerApplication implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbc;

    public static void main(String[] args) {
        SpringApplication.run(AppServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            System.out.println("RESULTADO-------->");
            System.out.println(jdbc.queryForObject("select count(*) from spatial_ref_sys",
                    Long.class));
            System.out.println("<--------");
        } catch (Exception e) {
            System.out.println("-------------When counting: " + e);
        }
    }
}
