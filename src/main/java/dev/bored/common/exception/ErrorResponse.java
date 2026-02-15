package dev.bored.common.exception;

import java.time.Instant;
import java.util.Map;

/**
 * Standardized error response body used across all services.
 * <p>
 * Provides a consistent JSON shape:
 * <pre>{
 *   "status": 404,
 *   "error": "Not Found",
 *   "message": "Experience not found with slug: xyz",
 *   "timestamp": "2026-02-15T10:30:00Z",
 *   "path": "/api/v1/experiences/xyz"
 * }</pre>
 * </p>
 *
 * @author Bored Software Developer
 * @since 2026-02-15
 */
public final class ErrorResponse {

    private ErrorResponse() { }

    /**
     * Builds a structured error map from a {@link GenericException}.
     *
     * @param ex   the exception
     * @param path the request path (nullable)
     * @return an unmodifiable map suitable for JSON serialization
     */
    public static Map<String, Object> of(GenericException ex, String path) {
        return Map.of(
                "status", ex.getStatus().value(),
                "error", ex.getStatus().getReasonPhrase(),
                "message", ex.getMessage(),
                "timestamp", Instant.now().toString(),
                "path", path != null ? path : ""
        );
    }

    /**
     * Builds a structured error map from status code / message.
     *
     * @param status  the HTTP status code (e.g. 500)
     * @param error   the HTTP status reason (e.g. "Internal Server Error")
     * @param message the detail message
     * @param path    the request path (nullable)
     * @return an unmodifiable map
     */
    public static Map<String, Object> of(int status, String error, String message, String path) {
        return Map.of(
                "status", status,
                "error", error != null ? error : "Unknown",
                "message", message != null ? message : "An unexpected error occurred",
                "timestamp", Instant.now().toString(),
                "path", path != null ? path : ""
        );
    }
}
