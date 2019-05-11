package com.wearewaes.assignment.diff.service.impl;

import com.google.common.base.Preconditions;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.wearewaes.assignment.diff.domain.comparator.DiffComparator;
import com.wearewaes.assignment.diff.domain.exception.DiffEntryNotFoundException;
import com.wearewaes.assignment.diff.domain.exception.MissingLeftOrRightValueException;
import com.wearewaes.assignment.diff.domain.exception.ServiceUnavailableException;
import com.wearewaes.assignment.diff.domain.integration.service.client.cache.CacheServiceClient;
import com.wearewaes.assignment.diff.domain.integration.service.client.cache.IntegrationDiffCacheEntry;
import com.wearewaes.assignment.diff.domain.model.Diff;
import com.wearewaes.assignment.diff.domain.model.DiffResponse;
import com.wearewaes.assignment.diff.domain.model.DiffResponseStatus;
import com.wearewaes.assignment.diff.domain.model.ValuePair;
import com.wearewaes.assignment.diff.service.DiffEvaluationService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Implementation of the diff evaluator service, it interfaces with cache service to
 * get and save computation results
 */
@Service
public class DiffEvaluationServiceImpl implements DiffEvaluationService {

    private CacheServiceClient cacheService;
    private DiffComparator<ValuePair, List<Diff>> diffComparator;

    public DiffEvaluationServiceImpl(CacheServiceClient cacheService,
                                     DiffComparator<ValuePair, List<Diff>> diffComparator) {
        this.cacheService = cacheService;
        this.diffComparator = diffComparator;
    }

    /**
     * This method is responsible to get differences between two values. It can return
     * a cached diff evaluation or evaluate a new entry containing left and right values
     * @param id the id for the diff entry containing left and right values
     * @return response containing all the differences between left and right value
     */
    @Override
    @HystrixCommand(fallbackMethod = "onCircuitOpen")
    public DiffResponse getDifference(String id) {
        checkId(id);
        IntegrationDiffCacheEntry diffEntry = cacheService.getCacheEntryById(id).getCacheEntry();
        checkDiffEntry(diffEntry);
        ValuePair valuePair = new ValuePair(diffEntry.getLeftValue(), diffEntry.getRightValue());
        // If Differences are already in cache, just return then
        if (!ObjectUtils.isEmpty(diffEntry.getDiffs())) {
            return new DiffResponse(id, valuePair, diffEntry.getDiffs());
        } else {
            // Otherwise, evaluate keypair
            return evaluateValuePair(id, valuePair);
        }
    }

    /**
     * Check if provided id is empty or null. If it is, throws a InvalidArgumentException
     * @param id diff entry id
     */
    private void checkId(String id) {
        Preconditions.checkArgument(!StringUtils.isEmpty(id), "Id cannot be null");
    }

    /**
     * Check if provided diff entry is null, if it is, throw DiffEntryNotFoundException
     * Then, checks if left or right value is missing, if it is, throws DiffEntryNotFoundException
     * @param diffEntry diff entry from cache
     */
    private void checkDiffEntry(IntegrationDiffCacheEntry diffEntry) {
        if (ObjectUtils.isEmpty(diffEntry)) {
            throw new DiffEntryNotFoundException();
        }
        if (isLeftOrRightValueMissing(diffEntry)) {
            throw new MissingLeftOrRightValueException();
        }
    }

    /**
     * Check if left or right value is missing in a diff entry from cache
     * @param diffEntry diff entry from cache
     * @return true if left or right value is missing
     */
    private boolean isLeftOrRightValueMissing(IntegrationDiffCacheEntry diffEntry) {
        return StringUtils.isEmpty(diffEntry.getLeftValue())
                || StringUtils.isEmpty(diffEntry.getRightValue());
    }

    /**
     * Evaluate left and right value key pair
     * If they are equal, return a ARE_EQUAL response
     * If they have different sizes, return a HAVE_DIFFERENT_SIZE response
     * Otherwise, runs a O(n) comparator and return a HAVE_DIFFERENCE with diffs
     * @param id diff entry id in cache
     * @param valuePair left and right value pair
     * @return response having the evaluation result
     */
    private DiffResponse evaluateValuePair(String id, ValuePair valuePair) {
        if (valuePair.areEqual()) {
            return new DiffResponse(id, valuePair, DiffResponseStatus.ARE_EQUAL);
        }
        if (valuePair.haveDifferentSize()) {
            return new DiffResponse(id, valuePair, DiffResponseStatus.HAVE_DIFFERENT_SIZE);
        }
        // Compare both values
        List<Diff> valueDifferences = diffComparator.compare(valuePair);
        updateValueDifferencesInCache(id, valueDifferences);
        return new DiffResponse(id, valuePair, valueDifferences);
    }

    /**
     * Calls cache service to update result of value pair evaluation
     * @param id diff entry in cache
     * @param valueDifferences list of differences between left a right values
     */
    private void updateValueDifferencesInCache(String id, List<Diff> valueDifferences) {
        IntegrationDiffCacheEntry diffEntry = cacheService.getCacheEntryById(id).getCacheEntry();
        diffEntry.setDiffs(valueDifferences);
        cacheService.saveCacheEntry(id, diffEntry);
    }

    /**
     * Fallback method that will be called in case circuit open for getDifference method,
     * indicating that services are not available, or there's an error that can degrade
     * application health and cascade to a worse error
     */
    public void onCircuitOpen() {
        throw new ServiceUnavailableException();
    }
}
