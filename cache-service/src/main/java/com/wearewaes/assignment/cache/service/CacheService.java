package com.wearewaes.assignment.cache.service;

import com.wearewaes.assignment.cache.domain.model.CacheResponse;
import com.wearewaes.assignment.cache.domain.model.DiffCacheEntry;

/**
 * This class defines the contract for a client that interfaces with cache service
 */
public interface CacheService {
    CacheResponse get(String id);
    CacheResponse save(String id, DiffCacheEntry cacheEntry);
}
