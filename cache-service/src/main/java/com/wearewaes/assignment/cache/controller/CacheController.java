package com.wearewaes.assignment.cache.controller;

import com.wearewaes.assignment.cache.domain.model.CacheResponse;
import com.wearewaes.assignment.cache.domain.model.DiffCacheEntry;
import com.wearewaes.assignment.cache.service.CacheService;
import org.springframework.web.bind.annotation.*;

/**
 * REST endpoints to get and save values in cache
 */
@RestController
@RequestMapping("/v1/cache")
public class CacheController {

    private CacheService service;

    public CacheController(CacheService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public CacheResponse get(@PathVariable("id") final String id) {
        return service.get(id);
    }

    @PostMapping("/{id}")
    public CacheResponse save(@PathVariable("id") final String id,
                              @RequestBody final DiffCacheEntry entryToBeCached) {
        return service.save(id, entryToBeCached);
    }
}
