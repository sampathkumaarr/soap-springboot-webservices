package com.mycompany.soapwebservice.spring.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.mycompany.*")
public class EmployeeDetailsSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeDetailsSpringBootApplication.class);
    }
}