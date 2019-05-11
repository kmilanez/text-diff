package com.wearewaes.assignment.cache.integration

import com.wearewaes.assignment.cache.controller.CacheController
import com.wearewaes.assignment.cache.domain.cache.impl.HazelCastMemoryCacheImpl
import com.wearewaes.assignment.cache.integration.config.HazelCastTestConfig
import com.wearewaes.assignment.cache.integration.mock.MockedIntegrationCacheResponse
import com.wearewaes.assignment.cache.integration.mock.MockedIntegrationDiffCacheEntries
import com.wearewaes.assignment.cache.integration.util.JsonContentSerializer
import com.wearewaes.assignment.cache.service.impl.CacheServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Import(HazelCastTestConfig)
@ContextConfiguration(classes = [
        HazelCastMemoryCacheImpl.class,
        CacheServiceImpl.class,
        CacheController.class
])
@WebMvcTest(CacheController)
class SaveAndGetDiffSpec extends Specification {

    @Autowired
    private HazelCastMemoryCacheImpl cache

    @Autowired
    private MockMvc mvc

    def setup() {
        cache.write(MockedIntegrationDiffCacheEntries.TEST_2.getId(), MockedIntegrationDiffCacheEntries.TEST_2)
    }

    private static final String ENDPOINT = "/v1/cache/{id}"

    def "Should create a new cache entry if its not in cache or update"() {
        def cacheEntry = MockedIntegrationDiffCacheEntries.TEST_1
        expect: "Status is 200 and the response is a cache entry"
            mvc.perform(post(ENDPOINT.replace("{id}", cacheEntry.getId()))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(JsonContentSerializer.serialize(cacheEntry)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(content().string(JsonContentSerializer
                            .serialize(MockedIntegrationCacheResponse.TEST_1_RESPONSE)))
    }

    def "Should return a cache entry if its cached"() {
        def cacheEntry = MockedIntegrationDiffCacheEntries.TEST_2
        expect: "Status is 200 and the response is a cache entry"
            mvc.perform(get(ENDPOINT.replace("{id}", cacheEntry.getId())))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(content().string(JsonContentSerializer
                            .serialize(MockedIntegrationCacheResponse.TEST_2_RESPONSE)))
    }
}
