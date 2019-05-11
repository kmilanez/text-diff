package com.wearewaes.assignment.decode.domain.decoder.base64;


/**
 * Defines the contract for a base64 decoder
 * @param <R> input value type
 * @param <S> output value type
 */
public interface Base64Decoder<R, S> {
    S decode(R value);
}
