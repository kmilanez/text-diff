package com.wearewaes.assignment.diff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Starts REST API enabling registration in Eureka and service communication
 * using OpenFeign clients
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients
public class DiffServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiffServiceApplication.class, args);
	}

}
