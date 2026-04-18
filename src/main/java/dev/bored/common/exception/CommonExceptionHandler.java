package dev.bored.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * Shared global exception handler for servlet-based services.
 * <p>
 * Translates {@link GenericException} into structured JSON responses and
 * catches any other {@link RuntimeException} as a generic 500 so error
 * shapes stay consistent across services.
 * </p>
 *
 * <p>Registered via {@link dev.bored.common.CommonLibAutoConfiguration} —
 * services don't need to component-scan {@code dev.bored.common}.</p>
 *
 * @author Bored Software Developer
 * @since 2026-04-18
 */
@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    /** Handles structured application exceptions. */
    @ExceptionHandler(GenericException.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(GenericException ex) {
        log.warn("Error [{}]: {}", ex.getStatus().value(), ex.getMessage());
        return ResponseEntity.status(ex.getStatus())
                .body(ErrorResponse.of(ex, null));
    }

    /** Fallback handler for unexpected runtime errors. */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntime(RuntimeException ex) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        return ResponseEntity.internalServerError()
                .body(ErrorResponse.of(500, "Internal Server Error",
                        "An unexpected error occurred", null));
    }
}
