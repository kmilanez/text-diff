package com.wearewaes.assignment.diff.mock

import com.wearewaes.assignment.diff.domain.integration.service.client.cache.IntegrationCacheResponse

class MockedIntegrationCacheResponse {
    public static final IntegrationCacheResponse EMPTY_RESPONSE =
            new IntegrationCacheResponse(null)
    public static final IntegrationCacheResponse TEST1_RESPONSE =
            new IntegrationCacheResponse(MockedIntegrationDiffCacheEntry.TEST_1)
    public static final IntegrationCacheResponse TEST2_RESPONSE =
            new IntegrationCacheResponse(MockedIntegrationDiffCacheEntry.TEST_2)
    public static final IntegrationCacheResponse TEST3_RESPONSE =
            new IntegrationCacheResponse(MockedIntegrationDiffCacheEntry.TEST_3)
    public static final IntegrationCacheResponse TEST4_RESPONSE =
            new IntegrationCacheResponse(MockedIntegrationDiffCacheEntry.TEST_4)
    public static final IntegrationCacheResponse TEST5_RESPONSE =
            new IntegrationCacheResponse(MockedIntegrationDiffCacheEntry.TEST_5)
    public static final IntegrationCacheResponse TEST6_RESPONSE =
            new IntegrationCacheResponse(MockedIntegrationDiffCacheEntry.TEST_6)
}
