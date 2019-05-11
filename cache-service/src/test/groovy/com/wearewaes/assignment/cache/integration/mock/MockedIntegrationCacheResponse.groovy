package com.wearewaes.assignment.cache.integration.mock

import com.wearewaes.assignment.cache.domain.model.CacheResponse
import com.wearewaes.assignment.cache.domain.model.CacheResponseStatus


class MockedIntegrationCacheResponse {
    public static final CacheResponse TEST_1_RESPONSE = new CacheResponse(
            MockedIntegrationDiffCacheEntries.TEST_1,
            CacheResponseStatus.SAVED
    )

    public static final CacheResponse TEST_2_RESPONSE = new CacheResponse(
            MockedIntegrationDiffCacheEntries.TEST_2,
            CacheResponseStatus.OK
    )
}
