package com.wearewaes.assignment.diff.domain.integration.service.client.cache;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("cache-service")
public interface CacheServiceClient {

    @GetMapping("/v1/cache/{id}")
    IntegrationCacheResponse getCacheEntryById(@PathVariable("id") String id);

    @PostMapping("/v1/cache/{id}")
    IntegrationCacheResponse saveCacheEntry(@PathVariable("id") String id,
                                            @RequestBody IntegrationDiffCacheEntry entryToBeCached);
}
