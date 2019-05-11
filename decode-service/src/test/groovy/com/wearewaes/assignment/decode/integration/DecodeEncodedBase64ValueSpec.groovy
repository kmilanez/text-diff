package com.wearewaes.assignment.decode.integration

import com.wearewaes.assignment.decode.controller.Base64DecodeController
import com.wearewaes.assignment.decode.domain.decoder.base64.impl.Base64StringDecoderImpl
import com.wearewaes.assignment.decode.integration.mock.MockedIntegrationTestValues
import com.wearewaes.assignment.decode.service.impl.Base64DecodeServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ContextConfiguration(classes = [
        Base64StringDecoderImpl,
        Base64DecodeServiceImpl,
        Base64DecodeController
])
@WebMvcTest(Base64DecodeController)
class DecodeEncodedBase64ValueSpec extends Specification {

    @Autowired
    private MockMvc mvc

    private static final String ENDPOINT = "/v1/decode/base64"

    def "Should return a decoded message payload response when request has encoded message"() {
        expect: "Status is 200 and the response is 'Hello World!'"
            mvc.perform(post(ENDPOINT)
                   .contentType(MediaType.APPLICATION_JSON_UTF8)
                   .content(MockedIntegrationTestValues.TEST_1.encodedValue))
                .andExpect(status().isOk())
                .andExpect(content().string(MockedIntegrationTestValues.TEST_1.decodedValue))
    }


    def "Should return a JSON decoded payload response when request has encoded JSON payload"() {
        expect: "Status is 200 and the response is a JSON Payload"
            mvc.perform(post(ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(MockedIntegrationTestValues.TEST_2.encodedValue))
                .andExpect(status().isOk())
                .andExpect(content().string(MockedIntegrationTestValues.TEST_2.decodedValue))
    }
}
