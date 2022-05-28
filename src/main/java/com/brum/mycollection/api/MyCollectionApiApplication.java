package com.brum.mycollection.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class MyCollectionApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyCollectionApiApplication.class, args);
	}

}
