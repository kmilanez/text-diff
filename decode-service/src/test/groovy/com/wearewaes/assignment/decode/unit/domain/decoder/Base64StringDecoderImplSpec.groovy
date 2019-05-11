package com.wearewaes.assignment.decode.unit.domain.decoder

import com.wearewaes.assignment.decode.domain.decoder.base64.impl.Base64StringDecoderImpl
import com.wearewaes.assignment.decode.unit.mock.MockedUnitTestValues
import spock.lang.Specification
import static org.assertj.core.api.Assertions.assertThat


class Base64StringDecoderImplSpec extends Specification {

    private Base64StringDecoderImpl decoder

    def setup() {
        decoder = new Base64StringDecoderImpl()
    }

    def "Should throw NullPointerException when the value to be decoded is null"() {
        given:
            String encodedValue = null
        when:
            decoder.decode(encodedValue)
        then:
            thrown(NullPointerException)
    }

    def "Should return an empty value when the value is empty"() {
        given:
            String encodedValue = ""
        when:
            String decodedValue = decoder.decode(encodedValue)
        then:
            assertThat(decodedValue).isEmpty()
    }

    def "Should return decoded quotation when the value is a Feynman's quotation"() {
        given:
             String encodedValue = MockedUnitTestValues.TEST_3.encodedValue
             String decodedValue = MockedUnitTestValues.TEST_3.decodedValue
        when:
             String resp = decoder.decode(encodedValue)
        then:
            assertThat(resp).isEqualTo(decodedValue)
    }

    def "Should return decoded JSON payload when the value is a JSON payload"() {
        given:
            String encodedValue = MockedUnitTestValues.TEST_4.encodedValue
            String decodedValue = MockedUnitTestValues.TEST_4.decodedValue
        when:
            String resp = decoder.decode(encodedValue)
        then:
            assertThat(resp).isEqualTo(decodedValue)
    }
}
