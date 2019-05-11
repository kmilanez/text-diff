package com.wearewaes.assignment.cache.unit.service

import com.wearewaes.assignment.cache.domain.cache.MemoryCache
import com.wearewaes.assignment.cache.service.impl.CacheServiceImpl
import com.wearewaes.assignment.cache.unit.mock.MockedUnitDiffCacheEntries
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat
import static org.mockito.Mockito.when

class CacheServiceImplSpec extends Specification {

    @Mock
    private MemoryCache cache

    @InjectMocks
    private CacheServiceImpl service

    def setup() {
        MockitoAnnotations.initMocks(this)
    }

    def "Should thrown IllegalArgumentException when trying to get a cache entry with a null id"() {
        given:
            def cacheEntryId = null
        when:
            when(cache.read(cacheEntryId)).thenThrow(IllegalArgumentException)
            service.get(cacheEntryId)
        then:
            thrown(IllegalArgumentException)
    }

    def "Should thrown IllegalArgumentException when id for the cache entry is empty"() {
        given:
            def cacheEntryId = ""
        when:
            when(cache.read(cacheEntryId)).thenThrow(IllegalArgumentException)
            cache.read(cacheEntryId)
        then:
            thrown(IllegalArgumentException)
    }

    def "Should return a response with the cache entry if it's in cache"() {
        given:
            def entryId = MockedUnitDiffCacheEntries.TEST_1.getId()
            def entry = MockedUnitDiffCacheEntries.TEST_1
        when:
            when(cache.read(entryId)).thenReturn(entry)
            def response = service.get(entryId)
        then:
            assertThat(response).isNotNull()
            assertThat(response.getCacheEntry()).isNotNull()
            assertThat(response.getCacheEntry().getId()).isEqualTo(entry.getId())
            assertThat(response.getCacheEntry().getRightValue()).isEqualTo(entry.getRightValue())
            assertThat(response.getCacheEntry().getLeftValue()).isEqualTo(entry.getLeftValue())
            assertThat(response.getCacheEntry().getDiffs()).isEqualTo(entry.getDiffs())
    }

    def "Should write the entry in cache and return it in the response"() {
        given:
            def entryId = MockedUnitDiffCacheEntries.TEST_2.getId()
            def entry = MockedUnitDiffCacheEntries.TEST_2
        when:
            when(cache.write(entryId, entry)).thenReturn(entry)
            def response = service.save(entryId, entry)
        then:
            assertThat(response).isNotNull()
            assertThat(response.getCacheEntry()).isNotNull()
            assertThat(response.getCacheEntry().getId()).isEqualTo(entry.getId())
            assertThat(response.getCacheEntry().getRightValue()).isEqualTo(entry.getRightValue())
            assertThat(response.getCacheEntry().getLeftValue()).isEqualTo(entry.getLeftValue())
            assertThat(response.getCacheEntry().getDiffs()).isEqualTo(entry.getDiffs())
    }

}
