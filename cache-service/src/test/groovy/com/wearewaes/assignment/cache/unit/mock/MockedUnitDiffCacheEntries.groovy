package com.wearewaes.assignment.cache.unit.mock

import com.wearewaes.assignment.cache.domain.model.CacheDiff
import com.wearewaes.assignment.cache.domain.model.DiffCacheEntry

class MockedUnitDiffCacheEntries {
    static final DiffCacheEntry TEST_1 =
            new DiffCacheEntry("1111", "{\"value\":\"Brasil\"}", "{\"value\":\"Brazil\"}")

    static final DiffCacheEntry TEST_2 =
            new DiffCacheEntry("1112", "{\"word\":\"Hello\"}", "{\"word\":\"Hallo\"}",
                    Arrays.asList(new CacheDiff(1, 1)))
}
