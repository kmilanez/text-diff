package com.wearewaes.assignment.cache.integration.mock

import com.wearewaes.assignment.cache.domain.model.DiffCacheEntry

class MockedIntegrationDiffCacheEntries {
    static final DiffCacheEntry TEST_1 =
            new DiffCacheEntry("1111", "{\"value\":\"SomeValue\"}", "{\"value\":\"someValue\"}")

    static final DiffCacheEntry TEST_2 =
            new DiffCacheEntry("1112", "{\"animal\":\"Cat\"}", "{\"animal\":\"Dog\"}")
}
