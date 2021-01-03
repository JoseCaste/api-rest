package com.jose.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.jose.api.restController.GreetingController;

@SpringBootApplication
//@ComponentScan(basePackageClasses = GreetingController.class)
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
 
}
