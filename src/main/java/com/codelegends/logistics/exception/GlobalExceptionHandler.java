package com.codelegends.logistics.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Translates validation, business, persistence, and unexpected exceptions into consistent HTTP responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    /** Converts missing active resources into HTTP 404 responses. */
    public ResponseEntity<ErrorResponse> missing(ResourceNotFoundException e) {
        return error(404, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    /** Converts bean validation failures into field-level HTTP 400 messages. */
    public ResponseEntity<ErrorResponse> validation(MethodArgumentNotValidException e) {
        return error(
                400,
                e.getBindingResult().getFieldErrors().stream()
                        .map(f -> f.getField() + ": " + f.getDefaultMessage())
                        .collect(Collectors.joining("; ")));
    }

    @ExceptionHandler({BusinessException.class, IllegalArgumentException.class})
    /** Converts business-rule and argument failures into HTTP 400 responses. */
    public ResponseEntity<ErrorResponse> business(RuntimeException e) {
        return error(400, e.getMessage());
    }

    @ExceptionHandler({
        HttpMessageNotReadableException.class,
        MethodArgumentTypeMismatchException.class
    })
    /** Converts malformed JSON, enum, date, and parameter values into HTTP 400 responses. */
    public ResponseEntity<ErrorResponse> malformed(Exception e) {
        return error(400, "Invalid JSON, enum, date or parameter type");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    /** Converts database constraint violations into client-readable HTTP 400 responses. */
    public ResponseEntity<ErrorResponse> duplicate(Exception e) {
        return error(400, "Duplicate unique value or invalid database constraint");
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    /** Converts optimistic-locking failures into retryable client errors. */
    public ResponseEntity<ErrorResponse> concurrent(Exception e) {
        return error(400, "Record changed concurrently; reload and retry");
    }

    @ExceptionHandler(Exception.class)
    /** Logs unexpected failures and returns a generic HTTP 500 response. */
    public ResponseEntity<ErrorResponse> unexpected(Exception e) {
        org.slf4j.LoggerFactory.getLogger(getClass()).error("Unexpected request failure", e);
        return error(500, "Unexpected server error");
    }

    /** Builds the standardized error response body and status code. */
    private ResponseEntity<ErrorResponse> error(int status, String message) {
        return ResponseEntity.status(status)
                .body(
                        new ErrorResponse(
                                status,
                                HttpStatus.valueOf(status).getReasonPhrase(),
                                message,
                                LocalDateTime.now()));
    }
}
