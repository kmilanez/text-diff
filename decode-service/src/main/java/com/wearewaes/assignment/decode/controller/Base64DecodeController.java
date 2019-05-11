package com.wearewaes.assignment.decode.controller;

import com.wearewaes.assignment.decode.service.Base64DecodeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for base64 decode
 */
@RestController
@RequestMapping("/v1/decode/base64")
public class Base64DecodeController {

    private Base64DecodeService service;

    public Base64DecodeController(Base64DecodeService service) {
        this.service = service;
    }

    @PostMapping
    public String decode(@RequestBody String value) {
        return service.decode(value);
    }
}
