package com.wearewaes.assignment.diff.domain.exception;

/**
 * Exception that represents a service(s) unavailability
 */
public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException() {
        super("Our service is unavailable at the moment and we are sorry for that. It's usually " +
                "fixed by retrying, but you can also contact support any time you need.");
    }
}
