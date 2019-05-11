package com.wearewaes.assignment.diff.domain.integration.service.client.decode;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("decode-service")
public interface DecodeServiceClient {
    @PostMapping(value = "/v1/decode/base64")
    String decode(@RequestBody String value);
}
