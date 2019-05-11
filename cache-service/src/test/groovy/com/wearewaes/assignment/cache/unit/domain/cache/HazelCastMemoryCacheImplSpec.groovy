package com.wearewaes.assignment.cache.unit.domain.cache

import com.hazelcast.core.HazelcastInstance
import com.hazelcast.core.ReplicatedMap
import com.wearewaes.assignment.cache.domain.cache.impl.HazelCastMemoryCacheImpl
import com.wearewaes.assignment.cache.unit.mock.MockedUnitDiffCacheEntries
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat
import static org.mockito.Mockito.when

class HazelCastMemoryCacheImplSpec extends Specification {

    @Mock
    private ReplicatedMap map

    @Mock
    private HazelcastInstance cacheProvider

    @InjectMocks
    private HazelCastMemoryCacheImpl cache

    def setup() {
        MockitoAnnotations.initMocks(this)
    }

    def "Should thrown IllegalArgumentException when id for the cache entry is null"() {
        given:
            def cacheEntryId = null
        when:
            when(map.get(cacheEntryId)).thenThrow(IllegalArgumentException)
            cache.read(cacheEntryId)
        then:
            thrown(IllegalArgumentException)
    }

    def "Should return null entry when id for the cache entry is empty"() {
        given:
            def cacheEntryId = ""
        when:
            when(map.get(cacheEntryId)).thenReturn(null)
            def cachedEntry = cache.read(cacheEntryId)
        then:
            assertThat(cachedEntry).isNull()
    }

    def "Should return the cache entry if its persisted in memory"() {
        given:
            def entryId = MockedUnitDiffCacheEntries.TEST_1.getId()
            def entry = MockedUnitDiffCacheEntries.TEST_1
        when:
            when(map.get(entryId)).thenReturn(MockedUnitDiffCacheEntries.TEST_1)
            def cachedEntry = cache.read(entryId)
        then:
            assertThat(cachedEntry).isNotNull()
            assertThat(cachedEntry.getId()).isEqualTo(entry.getId())
            assertThat(cachedEntry.getRightValue()).isEqualTo(entry.getRightValue())
            assertThat(cachedEntry.getLeftValue()).isEqualTo(entry.getLeftValue())
            assertThat(cachedEntry.getDiffs()).isEqualTo(entry.getDiffs())
    }

    def "Should write the entry in cache and return it"() {
        given:
            def entryId = MockedUnitDiffCacheEntries.TEST_2.getId()
            def entry = MockedUnitDiffCacheEntries.TEST_2
        when:
            when(map.put(entryId, entry)).thenReturn(entry)
            def cachedEntry = cache.write(entryId, entry)
        then:
            assertThat(cachedEntry).isNotNull()
            assertThat(cachedEntry.getId()).isEqualTo(entry.getId())
            assertThat(cachedEntry.getRightValue()).isEqualTo(entry.getRightValue())
            assertThat(cachedEntry.getLeftValue()).isEqualTo(entry.getLeftValue())
            assertThat(cachedEntry.getDiffs()).isEqualTo(entry.getDiffs())
    }

}
