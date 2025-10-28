package com.manav.JotDown;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class JournalApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(JournalApplication.class, args);
        System.out.println(context.getEnvironment().getActiveProfiles()[0]);
    }
}