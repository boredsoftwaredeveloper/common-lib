package dev.bored.common.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.*;

class GenericExceptionTest {

    @Test
    void constructor_withMessageAndStatus() {
        GenericException ex = new GenericException("Not found", HttpStatus.NOT_FOUND);
        assertThat(ex.getMessage()).isEqualTo("Not found");
        assertThat(ex.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void constructor_withMessageOnly_defaults500() {
        GenericException ex = new GenericException("boom");
        assertThat(ex.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void constructor_withCause() {
        RuntimeException cause = new RuntimeException("root");
        GenericException ex = new GenericException("wrapped", HttpStatus.BAD_GATEWAY, cause);
        assertThat(ex.getCause()).isEqualTo(cause);
        assertThat(ex.getStatus()).isEqualTo(HttpStatus.BAD_GATEWAY);
    }

    @Test
    void isRuntimeException() {
        assertThat(new GenericException("test")).isInstanceOf(RuntimeException.class);
    }
}
