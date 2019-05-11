package com.wearewaes.assignment.diff.service;

import com.wearewaes.assignment.diff.domain.model.DiffResponse;

/**
 * This interface defines the contract for saving left and right values
 */
public interface SaveValuesService {
    DiffResponse saveLeftValue(String id, String leftValue);
    DiffResponse saveRightValue(String id, String rightValue);
}
