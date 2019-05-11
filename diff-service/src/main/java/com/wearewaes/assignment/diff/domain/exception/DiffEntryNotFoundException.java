package com.wearewaes.assignment.diff.domain.exception;

/**
 * Exception that represents a scenario where diff entry is not in cache
 */
public class DiffEntryNotFoundException extends RuntimeException {
    public DiffEntryNotFoundException() {
        super("Diff entry not found!");
    }
}
