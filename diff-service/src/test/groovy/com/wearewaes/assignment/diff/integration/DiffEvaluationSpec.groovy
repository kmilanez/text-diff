package com.wearewaes.assignment.diff.integration

import com.wearewaes.assignment.diff.controller.DiffEvaluationController
import com.wearewaes.assignment.diff.controller.DiffSaveValuesController
import com.wearewaes.assignment.diff.domain.comparator.ValuePairDiffComparator
import com.wearewaes.assignment.diff.domain.integration.service.client.cache.CacheServiceClient
import com.wearewaes.assignment.diff.mock.MockedDiffResponse
import com.wearewaes.assignment.diff.mock.MockedIntegrationCacheResponse
import com.wearewaes.assignment.diff.service.impl.DiffEvaluationServiceImpl
import com.wearewaes.assignment.diff.service.impl.SaveValuesServiceImpl
import com.wearewaes.assignment.diff.util.JsonToString
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.ArgumentMatchers.any
import static org.mockito.ArgumentMatchers.any
import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.when
import static org.mockito.Mockito.when
import static org.mockito.Mockito.when
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ContextConfiguration(classes = [
        ValuePairDiffComparator,
        DiffEvaluationServiceImpl,
        DiffEvaluationController
])
@WebMvcTest(controllers = [DiffEvaluationController])
class DiffEvaluationSpec extends Specification {

    @Autowired
    private MockMvc mvc

    @MockBean
    private CacheServiceClient cacheService

    private static final String ARE_EQUAL_ENDPOINT = "/v1/diff/12343"
    private static final String HAVE_DIFFERENT_SIZE_ENDPOINT = "/v1/diff/12344"
    private static final String HAVE_DIFFERENCES_ENDPOINT = "/v1/diff/12345"

    def "Should return a ARE_EQUAL response since both left and right values are the same"() {
        when(cacheService.getCacheEntryById(any())).thenReturn(MockedIntegrationCacheResponse.TEST4_RESPONSE)
        expect: "Status is 200 and the response is a SAVED response type with 'Hello' decoded value"
        mvc.perform(get(ARE_EQUAL_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(content().string(JsonToString.convert(MockedDiffResponse.TEST_4_DIFF_RESPONSE)))
    }

    def "Should return a HAVE_DIFFERENT_SIZE response since both left and right values have different length"() {
        when(cacheService.getCacheEntryById(any())).thenReturn(MockedIntegrationCacheResponse.TEST5_RESPONSE)
        expect: "Status is 200 and the response is a SAVED response type with 'Hello' decoded value"
        mvc.perform(get(HAVE_DIFFERENT_SIZE_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(content().string(JsonToString.convert(MockedDiffResponse.TEST_5_DIFF_RESPONSE)))
    }

    def "Should return a HAVE_DIFFERENCES response having diffs [(11,1)]"() {
        when(cacheService.getCacheEntryById(any())).thenReturn(MockedIntegrationCacheResponse.TEST6_RESPONSE)
        expect: "Status is 200 and the response is a SAVED response type with 'Hello' decoded value"
        mvc.perform(get(HAVE_DIFFERENCES_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(content().string(JsonToString.convert(MockedDiffResponse.TEST_6_DIFF_RESPONSE)))
    }


}
