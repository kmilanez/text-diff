package com.wearewaes.assignment.diff.unit.service

import com.wearewaes.assignment.diff.domain.comparator.DiffComparator
import com.wearewaes.assignment.diff.domain.exception.DiffEntryNotFoundException
import com.wearewaes.assignment.diff.domain.exception.MissingLeftOrRightValueException
import com.wearewaes.assignment.diff.domain.integration.service.client.cache.CacheServiceClient
import com.wearewaes.assignment.diff.domain.integration.service.client.cache.IntegrationCacheResponse
import com.wearewaes.assignment.diff.domain.model.Diff
import com.wearewaes.assignment.diff.domain.model.DiffResponseStatus
import com.wearewaes.assignment.diff.domain.model.ValuePair
import com.wearewaes.assignment.diff.service.impl.DiffEvaluationServiceImpl
import com.wearewaes.assignment.diff.mock.MockedIntegrationCacheResponse
import com.wearewaes.assignment.diff.mock.MockedIntegrationDiffCacheEntry
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat
import static org.mockito.Mockito.when

class DiffEvaluationServiceImplSpec extends Specification {

    @Mock
    private CacheServiceClient cacheService
    @Mock
    private DiffComparator<ValuePair, List<Diff>> diffComparator

    @InjectMocks
    private DiffEvaluationServiceImpl service

    def setup() {
        MockitoAnnotations.initMocks(this)
    }

    def "Should throw an IllegalArgumentException when trying to get value diffs with a null Id"() {
        given:
            def diffValueId = null
        when:
            service.getDifference(diffValueId)
        then:
            thrown(IllegalArgumentException)
    }

    def "Should throw an IllegalArgumentException when trying to get value diffs with an empty Id"() {
        given:
            def diffValueId = ""
        when:
            service.getDifference(diffValueId)
        then:
            thrown(IllegalArgumentException)
    }

    def "Should throw DiffEntryNotFoundException when cache entry does not exist for value diffs"() {
        given:
            def diffValueId = "12339"
            def response = new IntegrationCacheResponse(null)
        when:
            when(cacheService.getCacheEntryById(diffValueId)).thenReturn(response)
            service.getDifference(diffValueId)
        then:
            thrown(DiffEntryNotFoundException)
    }


    def "Should throw MissingLeftOrRightValueException when right value is missing"() {
        given:
            def diffValueId = MockedIntegrationDiffCacheEntry.TEST_1.getId()
            def response = MockedIntegrationCacheResponse.TEST1_RESPONSE
        when:
            when(cacheService.getCacheEntryById(diffValueId)).thenReturn(response)
            service.getDifference(diffValueId)
        then:
            thrown(MissingLeftOrRightValueException)
    }

    def "Should throw MissingLeftOrRightValueException when left value is missing"() {
        given:
            def diffValueId = MockedIntegrationDiffCacheEntry.TEST_2.getId()
            def response = MockedIntegrationCacheResponse.TEST2_RESPONSE
        when:
            when(cacheService.getCacheEntryById(diffValueId)).thenReturn(response)
            service.getDifference(diffValueId)
        then:
            thrown(MissingLeftOrRightValueException)
    }

    def "Should return diffs from cache when they already have been compared"() {
        given:
            def diffValueId = MockedIntegrationDiffCacheEntry.TEST_3.getId()
            def diffEntry = MockedIntegrationDiffCacheEntry.TEST_3
            def cacheServiceResponse = MockedIntegrationCacheResponse.TEST3_RESPONSE
        when:
            when(cacheService.getCacheEntryById(diffValueId)).thenReturn(cacheServiceResponse)
            def response = service.getDifference(diffValueId)
        then:
            assertThat(response).isNotNull()
            assertThat(response.getDiffs()).isEqualTo(diffEntry.getDiffs())
    }

    def "Should return EQUAL response when left and right are equal"() {
        given:
            def diffValueId = MockedIntegrationDiffCacheEntry.TEST_4.getId()
            def cacheServiceResponse = MockedIntegrationCacheResponse.TEST4_RESPONSE
        when:
            when(cacheService.getCacheEntryById(diffValueId)).thenReturn(cacheServiceResponse)
            def response = service.getDifference(diffValueId)
        then:
            assertThat(response).isNotNull()
            assertThat(response.getStatus().name()).isEqualTo(DiffResponseStatus.ARE_EQUAL.name())
    }

    def "Should return HAVE_DIFFERENT_LENGTH response when left and right have different lengths"() {
        given:
            def diffValueId = MockedIntegrationDiffCacheEntry.TEST_5.getId()
            def cacheServiceResponse = MockedIntegrationCacheResponse.TEST5_RESPONSE
        when:
            when(cacheService.getCacheEntryById(diffValueId)).thenReturn(cacheServiceResponse)
            def response = service.getDifference(diffValueId)
        then:
            assertThat(response).isNotNull()
            assertThat(response.getStatus().name()).isEqualTo(DiffResponseStatus.HAVE_DIFFERENT_SIZE.name())
    }

    def "Should return HAVE_DIFFERENCES response when left and right have differences"() {
        given:
            def diffValueId = MockedIntegrationDiffCacheEntry.TEST_6.getId()
            def diffEntry = MockedIntegrationDiffCacheEntry.TEST_6
            def valuePair = new ValuePair(diffEntry.getLeftValue(), diffEntry.getRightValue())
            def cacheServiceResponse = MockedIntegrationCacheResponse.TEST6_RESPONSE
        when:
            when(cacheService.getCacheEntryById(diffValueId)).thenReturn(cacheServiceResponse)
            when(diffComparator.compare(valuePair)).thenReturn(diffEntry.getDiffs())
            def response = service.getDifference(diffValueId)
        then:
            assertThat(response).isNotNull()
            assertThat(response.getStatus().name()).isEqualTo(DiffResponseStatus.HAVE_DIFFERENCES.name())
            assertThat(response.getDiffs()).isEqualTo(diffEntry.getDiffs())
    }

}
