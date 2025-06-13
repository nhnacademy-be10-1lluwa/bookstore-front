package com.nhnacademy.illuwa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IlluwaApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(IlluwaApplication.class);
        app.run(args);
    }
}
