package com.teach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.teach.arthas")
public class TeachDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeachDemoApplication.class, args);
    }

}
