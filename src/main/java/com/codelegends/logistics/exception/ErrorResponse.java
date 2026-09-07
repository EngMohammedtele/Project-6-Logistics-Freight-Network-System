package com.codelegends.logistics.exception;

import java.time.LocalDateTime;

/**
 * Represents the standardized JSON error payload returned by the API.
 */
public record ErrorResponse(int status, String error, String message, LocalDateTime timestamp) {}
