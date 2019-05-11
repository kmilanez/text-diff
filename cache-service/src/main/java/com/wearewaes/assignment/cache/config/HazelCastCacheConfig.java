package com.wearewaes.assignment.cache.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.wearewaes.assignment.cache.domain.constant.CacheName;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Defines the configuration necessary to construct a local distributed-ready
 * HazelCast in-memory instance
 */
@Configuration
public class HazelCastCacheConfig {

    @Bean
    public HazelcastInstance cacheProvider() {
        return Hazelcast.newHazelcastInstance(createConfig());
    }

    private Config createConfig() {
        Config config = new Config();

        MapConfig mapConfig = new MapConfig();
        mapConfig.setName(CacheName.DEFAULT_CACHE_NAME);
        mapConfig.setTimeToLiveSeconds((int) TimeUnit.MINUTES.toSeconds(5));
        config.addMapConfig(mapConfig);

        NetworkConfig network = config.getNetworkConfig();
        network.setPort(5701).setPortCount(20);
        network.setPortAutoIncrement(true);
        return config;
    }
}
