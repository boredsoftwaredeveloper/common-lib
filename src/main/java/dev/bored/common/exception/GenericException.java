package dev.bored.common.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * Generic application exception carrying an {@link HttpStatus}.
 * <p>
 * Shared across all microservices so every service produces a consistent
 * error contract. Each service's exception handler maps the status to the
 * appropriate HTTP response.
 * </p>
 *
 * @author Bored Software Developer
 * @since 2026-02-15
 */
public class GenericException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final HttpStatus status;

    /**
     * Creates a new exception with a message and HTTP status.
     *
     * @param message the detail message
     * @param status  the HTTP status to map to the response
     */
    public GenericException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    /**
     * Creates a new exception with a message, defaulting to 500.
     *
     * @param message the detail message
     */
    public GenericException(String message) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Creates a new exception with a message, status, and root cause.
     *
     * @param message the detail message
     * @param status  the HTTP status
     * @param cause   the root cause
     */
    public GenericException(String message, HttpStatus status, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    /**
     * Returns the HTTP status associated with this exception.
     *
     * @return the {@link HttpStatus}
     */
    public HttpStatus getStatus() {
        return status;
    }
}
