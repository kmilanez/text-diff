package com.wearewaes.assignment.cache.domain.model;

import lombok.*;

/**
 * This class abstract the response from cache service, it can
 * be a read or a write result
 * It uses Lombok to remove unnecessary code (get/setter, equal and hashcode, etc)
 */
@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CacheResponse {
    private DiffCacheEntry cacheEntry;
    private CacheResponseStatus status;
}
