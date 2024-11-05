package com.mansa.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class authorizationApplication {

	public static void main(String[] args) {
		SpringApplication.run(authorizationApplication.class, args);
	}

}
