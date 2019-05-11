package com.wearewaes.assignment.decode.domain.decoder.base64.impl;

import com.google.common.io.BaseEncoding;
import com.wearewaes.assignment.decode.domain.decoder.base64.Base64Decoder;
import org.springframework.stereotype.Component;

/**
 * Implements a base64 decoder that receive a encoded string hash and return
 * a decoded value
 * Using Guava for DRY purposes, but we could also use java.util.Base64 (or
 * even implement a distributed decoding algorithm)
 */
@Component
public class Base64StringDecoderImpl implements Base64Decoder<String, String> {
    @Override
    public String decode(String value) {
        return new String(BaseEncoding.base64().decode(value));
    }
}
