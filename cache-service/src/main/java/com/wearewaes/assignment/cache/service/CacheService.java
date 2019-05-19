package com.wearewaes.assignment.cache.service;

import com.wearewaes.assignment.cache.domain.model.CacheResponse;
import com.wearewaes.assignment.cache.domain.model.DiffCacheEntry;

/**
 * Defines the contract for a service that get and saves
 * values from cache
 */
public interface CacheService {
    CacheResponse get(final String id);
    CacheResponse save(final String id, final DiffCacheEntry cacheEntry);
}
