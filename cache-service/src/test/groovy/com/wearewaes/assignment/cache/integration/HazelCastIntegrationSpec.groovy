package com.wearewaes.assignment.cache.integration

import com.wearewaes.assignment.cache.domain.cache.impl.HazelCastMemoryCacheImpl
import com.wearewaes.assignment.cache.integration.config.HazelCastTestConfig
import com.wearewaes.assignment.cache.integration.mock.MockedIntegrationDiffCacheEntries
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

@ContextConfiguration(classes =
    [HazelCastMemoryCacheImpl]
)
@Import(HazelCastTestConfig)
class HazelCastIntegrationSpec extends Specification {

    @Autowired
    private HazelCastMemoryCacheImpl cache

    def setup() {
        cache.write(MockedIntegrationDiffCacheEntries.TEST_1.getId(), MockedIntegrationDiffCacheEntries.TEST_1)
    }

    def "Should throw IllegalArgumentException when trying to get a entry for a null key/id"() {
        given:
            def cacheEntryId = null
        when:
            cache.read(cacheEntryId)
        then:
            thrown(IllegalArgumentException)
    }

    def "Should return a null entry when trying to get a entry for a empty key/id"() {
        given:
            def cacheEntryId = ""
        when:
            def cacheEntry = cache.read(cacheEntryId)
        then:
            assertThat(cacheEntry).isNull()
    }

    def "Should return the cache entry correlated to id if it exists in cache"() {
        given:
            def entryId = MockedIntegrationDiffCacheEntries.TEST_1.getId()
            def entry = MockedIntegrationDiffCacheEntries.TEST_1
        when:
            def cachedEntry = cache.read(entryId)
        then:
            assertThat(cachedEntry).isNotNull()
            assertThat(cachedEntry.getId()).isEqualTo(entry.getId())
            assertThat(cachedEntry.getLeftValue()).isEqualTo(entry.getLeftValue())
            assertThat(cachedEntry.getRightValue()).isEqualTo(entry.getRightValue())
    }

    def "Should save entry to cache if id and data are in valid state"() {
        given:
            def entryToBeCachedId = MockedIntegrationDiffCacheEntries.TEST_2.getId()
            def entryToBeCached = MockedIntegrationDiffCacheEntries.TEST_2
        when:
            cache.write(entryToBeCachedId, entryToBeCached)
            def cachedEntry = cache.read(entryToBeCachedId)
        then:
            assertThat(cachedEntry).isNotNull()
            assertThat(cachedEntry.getId()).isEqualTo(entryToBeCached.getId())
            assertThat(cachedEntry.getLeftValue()).isEqualTo(entryToBeCached.getLeftValue())
            assertThat(cachedEntry.getRightValue()).isEqualTo(entryToBeCached.getRightValue())
    }

}
