package com.eaads.phosphor_service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.boot.SpringApplication;

@Configuration
@ComponentScan(basePackages = "com.eaads.phosphor_service,"
		+ "com.eaads.phosphor_servic.models.github")
public class AppConfig {
	public static void main(String[] args) {
		SpringApplication.run(AppConfig.class, args);
				
	}
}
