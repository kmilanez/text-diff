package com.wearewaes.assignment.diff.unit.mock

import com.wearewaes.assignment.diff.domain.integration.service.client.cache.IntegrationCacheResponse

class MockedUnitIntegrationCacheResponse {
    public static final IntegrationCacheResponse EMPTY_RESPONSE =
            new IntegrationCacheResponse(null)
    public static final IntegrationCacheResponse TEST1_RESPONSE =
            new IntegrationCacheResponse(MockedUnitIntegrationDiffCacheEntry.TEST_1)
    public static final IntegrationCacheResponse TEST2_RESPONSE =
            new IntegrationCacheResponse(MockedUnitIntegrationDiffCacheEntry.TEST_2)
    public static final IntegrationCacheResponse TEST3_RESPONSE =
            new IntegrationCacheResponse(MockedUnitIntegrationDiffCacheEntry.TEST_3)
    public static final IntegrationCacheResponse TEST4_RESPONSE =
            new IntegrationCacheResponse(MockedUnitIntegrationDiffCacheEntry.TEST_4)
    public static final IntegrationCacheResponse TEST5_RESPONSE =
            new IntegrationCacheResponse(MockedUnitIntegrationDiffCacheEntry.TEST_5)
    public static final IntegrationCacheResponse TEST6_RESPONSE =
            new IntegrationCacheResponse(MockedUnitIntegrationDiffCacheEntry.TEST_6)
}
