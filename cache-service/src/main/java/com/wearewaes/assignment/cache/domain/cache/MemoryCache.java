package com.wearewaes.assignment.cache.domain.cache;

import com.wearewaes.assignment.cache.domain.model.DiffCacheEntry;

/**
 * Defines the contract for a in-memory cache
 */
public interface MemoryCache {
    DiffCacheEntry read(String key);
    DiffCacheEntry write(String key, DiffCacheEntry cacheEntry);
}
