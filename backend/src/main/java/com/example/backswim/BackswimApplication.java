package com.example.backswim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BackswimApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackswimApplication.class, args);
    }

}
