package com.wearewaes.assignment.diff.service.impl;

import com.google.common.base.Preconditions;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.wearewaes.assignment.diff.domain.exception.ServiceUnavailableException;
import com.wearewaes.assignment.diff.domain.integration.service.client.cache.CacheServiceClient;
import com.wearewaes.assignment.diff.domain.integration.service.client.cache.IntegrationDiffCacheEntry;
import com.wearewaes.assignment.diff.domain.integration.service.client.decode.DecodeServiceClient;
import com.wearewaes.assignment.diff.domain.model.DiffResponse;
import com.wearewaes.assignment.diff.service.SaveValuesService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * Implementation of the service to save left and right values, it interfaces
 * with cache and decode service to decode base64 hashes and save or get values
 * from cache
 */
@Service
public class SaveValuesServiceImpl implements SaveValuesService {

    private CacheServiceClient cacheService;
    private DecodeServiceClient decodeService;

    public SaveValuesServiceImpl(CacheServiceClient cacheService,
                                 DecodeServiceClient decodeService) {
        this.cacheService = cacheService;
        this.decodeService = decodeService;
    }

    /**
     * Decodes base64 left value and create or update diff entry having it in case
     * In case anything fails, it opens the circuit to onCircuitOpen
     * @param id diff entry in cache
     * @param leftValue encoded left value
     * @return response with left value indicating it was saved
     */
    @Override
    @HystrixCommand(fallbackMethod = "saveValueDefault")
    public DiffResponse saveLeftValue(String id, String leftValue) {
        checkIdAndValue(id, leftValue);
        final String decodedLeftValue = decodeService.decode(leftValue);
        final IntegrationDiffCacheEntry diffEntry = cacheService.getCacheEntryById(id).getCacheEntry();
        if (ObjectUtils.isEmpty(diffEntry)) {
            createNewDiffEntry(id, true, decodedLeftValue);
        } else {
            updateEntry(diffEntry, true, decodedLeftValue);
        }
        return new DiffResponse(id, decodedLeftValue);
    }

    /**
     * Decodes base64 right value and create or update diff entry having it in case
     * In case anything fails, it opens the circuit to onCircuitOpen
     * @param id diff entry in cache
     * @param rightValue encoded right value
     * @return response with right value indicating it was saved
     */
    @Override
    @HystrixCommand(fallbackMethod = "saveValueDefault")
    public DiffResponse saveRightValue(String id, String rightValue) {
        checkIdAndValue(id, rightValue);
        final String decodedRightValue = decodeService.decode(rightValue);
        final IntegrationDiffCacheEntry diffEntry = cacheService.getCacheEntryById(id).getCacheEntry();
        if (ObjectUtils.isEmpty(diffEntry)) {
            createNewDiffEntry(id, false, decodedRightValue);
        } else {
            updateEntry(diffEntry, false, decodedRightValue);
        }
        return new DiffResponse(id, decodedRightValue);
    }

    /**
     * Check if id and valid provided by upstream system or user are not
     * null or empty
     * @param id provided by upstream system or user
     * @param value encoded provided by upstream system or user
     */
    private void checkIdAndValue(String id, String value) {
        Preconditions.checkArgument(!StringUtils.isEmpty(id),
                "Id cannot be null!");
        Preconditions.checkArgument(!StringUtils.isEmpty(value),
                "Value cannot be null!");
    }

    /**
     * Create a new diff entry for a given id, with left or right value,
     * depending on flow orientation, with the new provided value
     * @param id for diff entry in cache
     * @param fromLeft flow orientation, if true, it's from left
     * @param value new value to be updated
     */
    private void createNewDiffEntry(String id, boolean fromLeft, String value) {
        final IntegrationDiffCacheEntry newDiffEntry;
        if (fromLeft) {
            newDiffEntry = new IntegrationDiffCacheEntry(id, value, null);
        } else {
            newDiffEntry = new IntegrationDiffCacheEntry(id, null, value);
        }
        cacheService.saveCacheEntry(id, newDiffEntry);
    }

    /**
     * If cache already have a diff entry for a given id, update left or right value,
     * depending on flow orientation, with the new provided value
     * @param diffEntry cached diff entry
     * @param fromLeft flow orientation, if true, it's from left
     * @param value new value to be updated
     */
    private void updateEntry(IntegrationDiffCacheEntry diffEntry, boolean fromLeft, String value) {
        // If it's no new entry, just update value
        final IntegrationDiffCacheEntry updatedDiffEntry;
        if (fromLeft) {
            updatedDiffEntry = new IntegrationDiffCacheEntry(diffEntry.getId(), value, diffEntry.getRightValue());
        } else {
            updatedDiffEntry = new IntegrationDiffCacheEntry(diffEntry.getId(), diffEntry.getLeftValue(), value);
        }
        cacheService.saveCacheEntry(updatedDiffEntry.getId(), updatedDiffEntry);
    }

    /**
     * Fallback method that will be called in case circuit open for saveLeftValue and saveRightValue
     * methods, indicating that services might not be available, or there's an error that can degrade
     * application health and cascade to a worse error
     */
    public DiffResponse saveValueDefault(String id, String value) {
        throw new ServiceUnavailableException();
    }
}
