package com.wearewaes.assignment.decode.unit.service

import com.wearewaes.assignment.decode.domain.decoder.base64.Base64Decoder
import com.wearewaes.assignment.decode.unit.mock.MockedUnitTestValues
import com.wearewaes.assignment.decode.service.impl.Base64DecodeServiceImpl

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import spock.lang.Specification
import static org.mockito.Mockito.when

import static org.assertj.core.api.Assertions.assertThat

class Base64DecodeServiceImplSpec extends Specification {

    @Mock
    private Base64Decoder<String, String> decoder

    @InjectMocks
    private Base64DecodeServiceImpl service

    def setup() {
        MockitoAnnotations.initMocks(this)
    }

    def "Should thrown IllegalArgumentException on validate if value in request is null"() {
        given:
            String value = null
        when:
            service.decode(value)
        then:
            thrown(IllegalArgumentException)
    }

    def "Should thrown IllegalArgumentException on validate if value in request is empty"() {
        given:
            String encodedValue = ""
        when:
            service.decode(encodedValue)
        then:
            thrown(IllegalArgumentException)
    }

    def "Should return decoded quotation when the value is a Feynman's quotation"() {
        given:
            String encodedValue = MockedUnitTestValues.TEST_3.encodedValue
            String decodedValue = MockedUnitTestValues.TEST_3.decodedValue
        when:
            when(decoder.decode(encodedValue)).thenReturn(decodedValue)
            String resp = service.decode(encodedValue)
        then:
            assertThat(resp).isEqualTo(decodedValue)
    }

    def "Should return decoded JSON payload when the value is a JSON"() {
        given:
            String encodedValue = MockedUnitTestValues.TEST_4.encodedValue
            String decodedValue = MockedUnitTestValues.TEST_4.decodedValue
        when:
            when(decoder.decode(encodedValue)).thenReturn(decodedValue)
            String resp = service.decode(encodedValue)
        then:
            assertThat(resp).isEqualTo(decodedValue)
    }
}
