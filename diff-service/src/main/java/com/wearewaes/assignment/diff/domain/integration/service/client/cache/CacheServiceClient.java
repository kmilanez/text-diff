package com.wearewaes.assignment.diff.domain.integration.service.client.cache;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * This class defines the contract for a client that interfaces with cache service
 */
@FeignClient("cache-service")
public interface CacheServiceClient {

    @GetMapping("/v1/cache/{id}")
    IntegrationCacheResponse getCacheEntryById(@PathVariable("id") String id);

    @PostMapping("/v1/cache/{id}")
    IntegrationCacheResponse saveCacheEntry(@PathVariable("id") String id,
                                            @RequestBody IntegrationDiffCacheEntry entryToBeCached);
}
