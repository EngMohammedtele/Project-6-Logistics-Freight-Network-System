package com.codelegends.logistics.exception;

/**
 * Signals that a requested active resource could not be found.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
