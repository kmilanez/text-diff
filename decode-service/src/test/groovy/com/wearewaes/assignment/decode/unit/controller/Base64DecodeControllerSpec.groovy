package com.wearewaes.assignment.decode.unit.controller

import com.wearewaes.assignment.decode.controller.Base64DecodeController
import com.wearewaes.assignment.decode.service.Base64DecodeService
import com.wearewaes.assignment.decode.unit.mock.MockedUnitTestValues
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat
import static org.mockito.Mockito.when

class Base64DecodeControllerSpec extends Specification {

    @Mock
    private Base64DecodeService service

    @InjectMocks
    private Base64DecodeController controller

    def setup() {
        MockitoAnnotations.initMocks(this)
    }

    def "Should return an error response when request payload has null value"() {
        given:
            String value = null
        when:
            when(service.decode(null)).thenThrow(IllegalArgumentException.class)
            controller.decode(value)
        then:
            thrown(IllegalArgumentException)
    }

    def "Should return an error response when request payload has empty value"() {
        given:
            String value = ""
        when:
            when(service.decode("")).thenThrow(IllegalArgumentException.class)
            controller.decode(value)
        then:
            thrown(IllegalArgumentException)
    }

    def "Should return a decoded message payload response when request has encoded message"() {
        given:
            String value = MockedUnitTestValues.TEST_1.encodedValue
            String decodedValue = MockedUnitTestValues.TEST_1.decodedValue
        when:
            when(service.decode(value)).thenReturn(decodedValue)
            String resp = controller.decode(value)
        then:
            assertThat(resp).isEqualTo(decodedValue)
    }

    def "Should return a JSON decoded payload response when request has encoded JSON payload"() {
        given:
            String value = MockedUnitTestValues.TEST_2.encodedValue
            String decodedValue = MockedUnitTestValues.TEST_2.decodedValue
        when:
            when(service.decode(value)).thenReturn(decodedValue)
            String resp = controller.decode(value)
        then:
            assertThat(resp).isEqualTo(decodedValue)
    }

}
