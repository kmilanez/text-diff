package com.wearewaes.assignment.diff.domain.integration.service.client.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntegrationCacheResponse {
    private IntegrationDiffCacheEntry cacheEntry;
}
