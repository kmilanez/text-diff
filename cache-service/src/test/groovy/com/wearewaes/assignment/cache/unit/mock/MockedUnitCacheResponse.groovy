package com.wearewaes.assignment.cache.unit.mock

import com.wearewaes.assignment.cache.domain.model.CacheResponse
import com.wearewaes.assignment.cache.domain.model.CacheResponseStatus

class MockedUnitCacheResponse {
    public static final CacheResponse TEST_1_RESPONSE = new CacheResponse(
            MockedUnitDiffCacheEntries.TEST_1,
            CacheResponseStatus.SAVED
    )

    public static final CacheResponse TEST_2_RESPONSE = new CacheResponse(
            MockedUnitDiffCacheEntries.TEST_2,
            CacheResponseStatus.OK
    )
}
