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

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> missing(ResourceNotFoundException e) {
        return error(404, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validation(MethodArgumentNotValidException e) {
        return error(
                400,
                e.getBindingResult().getFieldErrors().stream()
                        .map(f -> f.getField() + ": " + f.getDefaultMessage())
                        .collect(Collectors.joining("; ")));
    }

    @ExceptionHandler({BusinessException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> business(RuntimeException e) {
        return error(400, e.getMessage());
    }

    @ExceptionHandler({
        HttpMessageNotReadableException.class,
        MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<ErrorResponse> malformed(Exception e) {
        return error(400, "Invalid JSON, enum, date or parameter type");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> duplicate(Exception e) {
        return error(400, "Duplicate unique value or invalid database constraint");
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<ErrorResponse> concurrent(Exception e) {
        return error(400, "Record changed concurrently; reload and retry");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> unexpected(Exception e) {
        org.slf4j.LoggerFactory.getLogger(getClass()).error("Unexpected request failure", e);
        return error(500, "Unexpected server error");
    }

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
