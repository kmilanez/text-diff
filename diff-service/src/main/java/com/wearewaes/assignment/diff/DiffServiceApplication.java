package com.wearewaes.assignment.diff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Bootstraps REST API enabling registration in Eureka and service communication
 * using OpenFeign clients
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class DiffServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiffServiceApplication.class, args);
	}

}
