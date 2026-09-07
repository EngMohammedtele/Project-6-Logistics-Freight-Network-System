package com.codelegends.logistics.exception;

/**
 * Signals business-rule violations that should be returned as client errors.
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
