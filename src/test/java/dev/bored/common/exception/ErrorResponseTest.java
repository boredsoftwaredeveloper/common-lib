package dev.bored.common.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class ErrorResponseTest {

    @Test
    void of_fromGenericException() {
        GenericException ex = new GenericException("not found", HttpStatus.NOT_FOUND);
        Map<String, Object> response = ErrorResponse.of(ex, "/api/v1/profiles/99");

        assertThat(response).containsEntry("status", 404);
        assertThat(response).containsEntry("error", "Not Found");
        assertThat(response).containsEntry("message", "not found");
        assertThat(response).containsEntry("path", "/api/v1/profiles/99");
        assertThat(response).containsKey("timestamp");
    }

    @Test
    void of_fromGenericException_nullPath() {
        GenericException ex = new GenericException("oops", HttpStatus.BAD_REQUEST);
        Map<String, Object> response = ErrorResponse.of(ex, null);
        assertThat(response).containsEntry("path", "");
    }

    @Test
    void of_fromRawValues() {
        Map<String, Object> response = ErrorResponse.of(500, "Internal Server Error", "boom", "/test");

        assertThat(response).containsEntry("status", 500);
        assertThat(response).containsEntry("error", "Internal Server Error");
        assertThat(response).containsEntry("message", "boom");
        assertThat(response).containsEntry("path", "/test");
    }

    @Test
    void of_fromRawValues_nullsSafe() {
        Map<String, Object> response = ErrorResponse.of(500, null, null, null);

        assertThat(response).containsEntry("error", "Unknown");
        assertThat(response).containsEntry("message", "An unexpected error occurred");
        assertThat(response).containsEntry("path", "");
    }
}
