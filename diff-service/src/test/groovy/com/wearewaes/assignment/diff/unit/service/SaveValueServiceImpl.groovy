package com.wearewaes.assignment.diff.unit.service

import com.wearewaes.assignment.diff.domain.integration.service.client.cache.CacheServiceClient
import com.wearewaes.assignment.diff.domain.integration.service.client.decode.DecodeServiceClient
import com.wearewaes.assignment.diff.domain.model.DiffResponseStatus
import com.wearewaes.assignment.diff.service.impl.SaveValuesServiceImpl
import com.wearewaes.assignment.diff.unit.mock.MockedUnitIntegrationCacheResponse
import com.wearewaes.assignment.diff.unit.mock.MockedUnitIntegrationDiffCacheEntry
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat
import static org.mockito.Mockito.when

class SaveValueServiceImpl extends Specification {

    @Mock
    private CacheServiceClient cacheService

    @Mock
    private DecodeServiceClient decodeService

    @InjectMocks
    private SaveValuesServiceImpl service

    def setup() {
        MockitoAnnotations.initMocks(this)
    }

    def "Should throw an IllegalArgumentException when trying to save right value with a empty Id"() {
        given:
            def id = null
            def value = "SGVsbG8="
        when:
            service.saveRightValue(id, value)
        then:
            thrown(IllegalArgumentException)
    }

    def "Should throw an IllegalArgumentException when trying to save right value with a empty value"() {
        given:
            def id = "12339"
            def value = ""
        when:
            service.saveRightValue(id, value)
        then:
            thrown(IllegalArgumentException)
    }


    def "Should throw an IllegalArgumentException when trying to save left value with a empty Id"() {
        given:
            def id = ""
            def value = "SGFsbG8="
        when:
            service.saveRightValue(id, value)
        then:
            thrown(IllegalArgumentException)
    }

    def "Should throw an IllegalArgumentException when trying to save left value with a empty value"() {
        given:
            def id = "12340"
            def value = ""
        when:
            service.saveRightValue(id, value)
        then:
            thrown(IllegalArgumentException)
    }

    def "Should create a new diff entry and save left value it when value is not cached yet"() {
        given:
            def id = MockedUnitIntegrationDiffCacheEntry.TEST_1.getId()
            def encodedValue = "SGVsbG8="
            def decodedValue = "Hello"
            def cacheEntry = MockedUnitIntegrationDiffCacheEntry.TEST_1
            def cacheResponse = MockedUnitIntegrationCacheResponse.TEST1_RESPONSE
        when:
            when(cacheService.getCacheEntryById(id)).thenReturn(MockedUnitIntegrationCacheResponse.EMPTY_RESPONSE)
            when(decodeService.decode(encodedValue)).thenReturn(decodedValue)
            when(cacheService.saveCacheEntry(id, cacheEntry)).thenReturn(cacheResponse)
            def response = service.saveLeftValue(id, encodedValue)
        then:
            assertThat(response).isNotNull()
            assertThat(response.getValue()).isEqualTo(decodedValue)
            assertThat(response.getStatus().name()).isEqualTo(DiffResponseStatus.SAVED.name())
    }

    def "Should create a new diff entry and save right value it when value is not cached yet"() {
        given:
            def id = MockedUnitIntegrationDiffCacheEntry.TEST_2.getId()
            def encodedValue = "SGFsbG8="
            def decodedValue = "Hallo"
            def cacheEntry = MockedUnitIntegrationDiffCacheEntry.TEST_2
            def cacheResponse = MockedUnitIntegrationCacheResponse.TEST2_RESPONSE
        when:
            when(cacheService.getCacheEntryById(id)).thenReturn(MockedUnitIntegrationCacheResponse.EMPTY_RESPONSE)
            when(decodeService.decode(encodedValue)).thenReturn(decodedValue)
            when(cacheService.saveCacheEntry(id, cacheEntry)).thenReturn(cacheResponse)
            def response = service.saveLeftValue(id, encodedValue)
        then:
            assertThat(response).isNotNull()
            assertThat(response.getValue()).isEqualTo(decodedValue)
            assertThat(response.getStatus().name()).isEqualTo(DiffResponseStatus.SAVED.name())
    }

    def "Should update an existing diff entry and save left value it when value is already cached "() {
        given:
            def id = MockedUnitIntegrationDiffCacheEntry.TEST_1.getId()
            def encodedValue = "SGFsbG8="
            def decodedValue = "Hallo"
            def cacheEntry = MockedUnitIntegrationDiffCacheEntry.TEST_1
            def cacheResponse = MockedUnitIntegrationCacheResponse.TEST1_RESPONSE
            def saveCacheResponse = MockedUnitIntegrationCacheResponse.TEST2_RESPONSE
        when:
            when(cacheService.getCacheEntryById(id)).thenReturn(cacheResponse)
            when(decodeService.decode(encodedValue)).thenReturn(decodedValue)
            when(cacheService.saveCacheEntry(id, cacheEntry)).thenReturn(saveCacheResponse)
            def response = service.saveLeftValue(id, encodedValue)
        then:
            assertThat(response).isNotNull()
            assertThat(response.getValue()).isEqualTo(decodedValue)
            assertThat(response.getStatus().name()).isEqualTo(DiffResponseStatus.SAVED.name())
    }

    def "Should update an existing diff entry and save right value it when value is already cached"() {
        given:
            def id = MockedUnitIntegrationDiffCacheEntry.TEST_3.getId()
            def encodedValue = "SGVsbG8="
            def decodedValue = "Hello"
            def cacheEntry = MockedUnitIntegrationDiffCacheEntry.TEST_3
            def cacheResponse = MockedUnitIntegrationCacheResponse.TEST3_RESPONSE
            def saveCacheResponse = MockedUnitIntegrationCacheResponse.TEST2_RESPONSE
        when:
            when(cacheService.getCacheEntryById(id)).thenReturn(cacheResponse)
            when(decodeService.decode(encodedValue)).thenReturn(decodedValue)
            when(cacheService.saveCacheEntry(id, cacheEntry)).thenReturn(saveCacheResponse)
            def response = service.saveLeftValue(id, encodedValue)
        then:
            assertThat(response).isNotNull()
            assertThat(response.getValue()).isEqualTo(decodedValue)
            assertThat(response.getStatus().name()).isEqualTo(DiffResponseStatus.SAVED.name())
    }

}
