package com.wearewaes.assignment.diff.service;

import com.wearewaes.assignment.diff.domain.model.DiffResponse;

/**
 * This interface defines the contract for difference evaluator service
 */
public interface DiffEvaluationService {
    DiffResponse getDifference(String id);
}
