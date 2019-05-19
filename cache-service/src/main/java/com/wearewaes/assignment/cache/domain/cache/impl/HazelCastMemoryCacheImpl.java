package com.wearewaes.assignment.cache.domain.cache.impl;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ReplicatedMap;
import com.wearewaes.assignment.cache.domain.cache.MemoryCache;
import com.wearewaes.assignment.cache.domain.constant.CacheName;
import com.wearewaes.assignment.cache.domain.model.DiffCacheEntry;
import org.springframework.stereotype.Component;

/**
 * Implements a in memory cache using Hazelcast
 */
@Component
public class HazelCastMemoryCacheImpl implements MemoryCache {

    private ReplicatedMap<String, DiffCacheEntry> cache;

    public HazelCastMemoryCacheImpl(HazelcastInstance cacheProvider) {
        this.cache = cacheProvider.getReplicatedMap(CacheName.DEFAULT_CACHE_NAME);
    }

    /**
     * Read cache entry using id from cache
     * @param key cache entry key
     * @return cache entry
     */
    @Override
    public DiffCacheEntry read(final String key) {
        return cache.get(key);
    }

    /**
     * Write cache entry in cache (it can be a brand new entry or an update)
     * It reads from cache before returning to avoid null evaluations for new
     * cache entries
     * @param key cache entry key
     * @return cache entry
     */
    @Override
    public DiffCacheEntry write(final String key, final DiffCacheEntry cacheEntry) {
        cache.put(key, cacheEntry);
        return read(key);
    }
}
