package com.wearewaes.assignment.decode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Starts REST API enabling registration in Eureka and service communication
 * using OpenFeign clients
 */
@SpringBootApplication
public class DecodeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DecodeServiceApplication.class, args);
	}

}
