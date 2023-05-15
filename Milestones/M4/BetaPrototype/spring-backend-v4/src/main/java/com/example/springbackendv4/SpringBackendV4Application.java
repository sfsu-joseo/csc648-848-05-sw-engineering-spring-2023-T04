package com.example.springbackendv4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class SpringBackendV4Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBackendV4Application.class, args);
	}

}
