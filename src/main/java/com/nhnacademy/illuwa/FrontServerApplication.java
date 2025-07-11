package com.nhnacademy.illuwa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FrontServerApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(FrontServerApplication.class);
        app.run(args);
    }
}
