package com.wearewaes.assignment.diff.mock

import com.wearewaes.assignment.diff.domain.integration.service.client.cache.IntegrationDiffCacheEntry
import com.wearewaes.assignment.diff.domain.model.Diff

class MockedIntegrationDiffCacheEntry {
    public static final IntegrationDiffCacheEntry TEST_1 = new IntegrationDiffCacheEntry("12340",
            "{\"value\":\"Hello!\"}", "")

    public static final IntegrationDiffCacheEntry TEST_2 = new IntegrationDiffCacheEntry("12341",
            "", "{\"value\":\"Hallo!\"}")

    public static final IntegrationDiffCacheEntry TEST_3 = new IntegrationDiffCacheEntry("12342",
            "{\"value\":\"Hello!\"}", "{\"value\":\"Hallo!\"}", [new Diff(1, 1)])

    public static final IntegrationDiffCacheEntry TEST_4 = new IntegrationDiffCacheEntry("12343",
            "{\"value\":\"Hello!\"}", "{\"value\":\"Hello!\"}")

    public static final IntegrationDiffCacheEntry TEST_5 = new IntegrationDiffCacheEntry("12344",
            "{\"value\":\"Good morning!\"}", "{\"value\":\"Guten tag!\"}")

    public static final IntegrationDiffCacheEntry TEST_6 = new IntegrationDiffCacheEntry("12345",
            "{\"value\":\"Hello!\"}", "{\"value\":\"Hallo!\"}", [new Diff(11, 1)])



}
