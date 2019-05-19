package com.wearewaes.assignment.cache.service.impl;

import com.google.common.base.Preconditions;
import com.wearewaes.assignment.cache.domain.cache.MemoryCache;
import com.wearewaes.assignment.cache.domain.model.CacheResponse;
import com.wearewaes.assignment.cache.domain.model.CacheResponseStatus;
import com.wearewaes.assignment.cache.domain.model.DiffCacheEntry;
import com.wearewaes.assignment.cache.service.CacheService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * Implementation of the cache service, it uses an memory cache
 * abstraction to read and write values from cache
 */
@Service
public class CacheServiceImpl implements CacheService {

    private MemoryCache cache;

    public CacheServiceImpl(MemoryCache cache) {
        this.cache = cache;
    }

    /**
     * Get entry from cache
     * @param id cache id
     * @return cache entry
     */
    @Override
    public CacheResponse get(final String id) {
        checkIfCacheIdIsEmpty(id);
        return new CacheResponse(cache.read(id), CacheResponseStatus.OK);
    }

    /**
     * Save value in cache
     * @param id new cache entry id
     * @return cache entry
     */
    @Override
    public CacheResponse save(final String id, final DiffCacheEntry cacheEntry) {
        checkIfCacheIdIsEmpty(id);
        checkIfCacheEntryIsEmpty(cacheEntry);
        return new CacheResponse(cache.write(id, cacheEntry), CacheResponseStatus.SAVED);
    }

    /**
     * Check if provided id is empty and throw IllegalArgumentException
     * if it is
     * @param id cache entry id
     */
    private void checkIfCacheIdIsEmpty(String id) {
        Preconditions.checkArgument(!StringUtils.isEmpty(id),
                "Cache Id cannot be empty!");
    }

    /**
     * Check if a given cache entry is empty and throw IllegalArgumentException
     * if it is
     * @param cacheEntry cache entry
     */
    private void checkIfCacheEntryIsEmpty(DiffCacheEntry cacheEntry) {
        Preconditions.checkArgument(!ObjectUtils.isEmpty(cacheEntry),
                "Cache entry cannot be empty!");
    }
}
