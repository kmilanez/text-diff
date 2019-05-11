package com.wearewaes.assignment.decode.service.impl;

import com.google.common.base.Preconditions;
import com.wearewaes.assignment.decode.domain.decoder.base64.Base64Decoder;
import com.wearewaes.assignment.decode.service.Base64DecodeService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Implementation of the base64 decode system, it uses a decoder abstraction
 * and doesn't have any implicit dependency to decode
 */
@Service
public class Base64DecodeServiceImpl implements Base64DecodeService {

    private Base64Decoder<String, String> decoder;

    public Base64DecodeServiceImpl(Base64Decoder<String, String> decoder) {
        this.decoder = decoder;
    }

    /**
     * Runs a Base64Decoder to decode value
     * @param value encoded value
     * @return decoded value
     */
    @Override
    public String decode(String value) {
        Preconditions.checkArgument(!StringUtils.isEmpty(value),
                "Value to be decoded cannot be null!");
        return decoder.decode(value);
    }
}
