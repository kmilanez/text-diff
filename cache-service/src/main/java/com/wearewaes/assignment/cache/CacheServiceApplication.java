package com.wearewaes.assignment.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Starts REST API enabling registration in Eureka and caching
 * with Hazelcast (but it can be extended to any other cache provider)
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
public class CacheServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(CacheServiceApplication.class, args);
	}
}
