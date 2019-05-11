package com.wearewaes.assignment.cache.integration.config

import com.hazelcast.config.Config
import com.hazelcast.config.MapConfig
import com.hazelcast.core.Hazelcast
import com.hazelcast.core.HazelcastInstance
import com.wearewaes.assignment.cache.domain.constant.CacheName
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HazelCastTestConfig {
    @Bean
    HazelcastInstance testCache() {
        return Hazelcast.newHazelcastInstance(createConfig())
    }

    private static Config createConfig() {
        Config config = new Config()
        MapConfig mapConfig = new MapConfig()
        mapConfig.setName(CacheName.DEFAULT_CACHE_NAME)
        return config
    }
}
