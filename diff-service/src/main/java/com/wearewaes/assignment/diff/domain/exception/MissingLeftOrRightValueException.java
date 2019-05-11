package com.wearewaes.assignment.diff.domain.exception;

/**
 * Exception that represents missing left or right value in diff entry
 */
public class MissingLeftOrRightValueException extends RuntimeException {
    public MissingLeftOrRightValueException() {
        super("Left or right value is missing!");
    }
}
