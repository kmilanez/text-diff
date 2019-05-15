package com.wearewaes.assignment.diff.integration

import com.wearewaes.assignment.diff.controller.DiffSaveValuesController
import com.wearewaes.assignment.diff.domain.integration.service.client.cache.CacheServiceClient
import com.wearewaes.assignment.diff.domain.integration.service.client.decode.DecodeServiceClient
import com.wearewaes.assignment.diff.mock.MockedDiffResponse
import com.wearewaes.assignment.diff.mock.MockedIntegrationCacheResponse
import com.wearewaes.assignment.diff.service.impl.SaveValuesServiceImpl
import com.wearewaes.assignment.diff.util.JsonToString

import static org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

@ContextConfiguration(classes = [
        SaveValuesServiceImpl,
        DiffSaveValuesController
])
@WebMvcTest(controllers = [DiffSaveValuesController])
class SaveValuesSpec extends Specification  {

    @Autowired
    private MockMvc mvc

    @MockBean
    private DecodeServiceClient decodeService

    @MockBean
    private CacheServiceClient cacheService

    private static final String LEFT_ENDPOINT = "/v1/diff/12340/left"
    private static final String RIGHT_ENDPOINT = "/v1/diff/12341/right"

    def "Should save a new decoded message as the left value when request has encoded message"() {
        when(decodeService.decode(any())).thenReturn("Hello")
        when(cacheService.getCacheEntryById(any())).thenReturn(MockedIntegrationCacheResponse.EMPTY_RESPONSE)
        when(cacheService.saveCacheEntry(any(), any())).thenReturn(MockedIntegrationCacheResponse.TEST1_RESPONSE)
        expect: "Status is 200 and the response is a SAVED response type with 'Hello' decoded value"
            mvc.perform(post(LEFT_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content("SGVsbG8="))
                    .andExpect(status().isOk())
                    .andExpect(content().string(JsonToString.convert(MockedDiffResponse.TEST_1_DIFF_RESPONSE)))
    }

    def "Should save a new decoded message as the right value when request has encoded message"() {
        when(decodeService.decode(any())).thenReturn("Hallo")
        when(cacheService.getCacheEntryById(any())).thenReturn(MockedIntegrationCacheResponse.EMPTY_RESPONSE)
        when(cacheService.saveCacheEntry(any(), any())).thenReturn(MockedIntegrationCacheResponse.TEST2_RESPONSE)
        expect: "Status is 200 and the response is a SAVED response type with 'Hallo' decoded value"
            mvc.perform(post(RIGHT_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content("SGVsbG8="))
                    .andExpect(status().isOk())
                    .andExpect(content().string(JsonToString.convert(MockedDiffResponse.TEST_2_DIFF_RESPONSE)))
    }

}
