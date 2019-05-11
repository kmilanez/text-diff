package com.wearewaes.assignment.cache.unit.controller

import com.wearewaes.assignment.cache.controller.CacheController
import com.wearewaes.assignment.cache.service.CacheService
import com.wearewaes.assignment.cache.unit.mock.MockedUnitCacheResponse
import com.wearewaes.assignment.cache.unit.mock.MockedUnitDiffCacheEntries
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat
import static org.mockito.Mockito.when

class CacheControllerSpec extends Specification {

    @Mock
    private CacheService service

    @InjectMocks
    private CacheController controller

    def setup() {
        MockitoAnnotations.initMocks(this)
    }

    def "Should thrown IllegalArgumentException when trying to get a cache entry with a null id"() {
        given:
            def cacheEntryId = null
        when:
            when(service.get(cacheEntryId)).thenThrow(IllegalArgumentException)
            controller.get(cacheEntryId)
        then:
            thrown(IllegalArgumentException)
    }

    def "Should thrown IllegalArgumentException when id for the cache entry is empty"() {
        given:
            def cacheEntryId = ""
        when:
            when(service.get(cacheEntryId)).thenThrow(IllegalArgumentException)
            controller.get(cacheEntryId)
        then:
            thrown(IllegalArgumentException)
    }

    def "Should return a response with the cache entry if it's in cache"() {
        given:
            def entryId = MockedUnitDiffCacheEntries.TEST_1.getId()
            def expectedResponse = MockedUnitCacheResponse.TEST_1_RESPONSE
        when:
            when(service.get(entryId)).thenReturn(expectedResponse)
            def response = controller.get(entryId)
        then:
            assertThat(response).isNotNull()
            assertThat(response).isEqualTo(expectedResponse)

    }

    def "Should write the entry in cache and return it in the response"() {
        given:
            def entryId = MockedUnitDiffCacheEntries.TEST_2.getId()
            def expectedResponse = MockedUnitCacheResponse.TEST_2_RESPONSE
        when:
            when(service.get(entryId)).thenReturn(expectedResponse)
            def response = controller.get(entryId)
        then:
            assertThat(response).isNotNull()
            assertThat(response).isEqualTo(expectedResponse)
    }

}
