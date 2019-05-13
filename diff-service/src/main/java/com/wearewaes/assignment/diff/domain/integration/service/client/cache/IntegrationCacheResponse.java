package com.wearewaes.assignment.diff.domain.integration.service.client.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

/**
 * This class abstract the response from cache service
 * It uses Lombok to remove unnecessary boilerplate code (get/setter, equal and hashcode, etc)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntegrationCacheResponse {
    private IntegrationDiffCacheEntry cacheEntry;
}
