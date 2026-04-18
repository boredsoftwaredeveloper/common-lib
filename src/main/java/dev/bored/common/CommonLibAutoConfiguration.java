package dev.bored.common;

import dev.bored.common.exception.CommonExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Root auto-configuration for the shared common-lib beans.
 * <p>
 * Registered in {@code META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports}
 * so services automatically get:
 * <ul>
 *   <li>{@link CommonExceptionHandler} — uniform error responses (servlet only).</li>
 * </ul>
 * CORS beans live in their own conditional configs in
 * {@code dev.bored.common.web} so they fire only for the matching stack
 * (servlet vs reactive).
 * </p>
 *
 * @author Bored Software Developer
 * @since 2026-04-18
 */
@AutoConfiguration
public class CommonLibAutoConfiguration {

    /** Servlet-only exception handler — not needed by the reactive gateway. */
    @Bean
    @ConditionalOnClass(WebMvcConfigurer.class)
    @ConditionalOnMissingBean
    public CommonExceptionHandler commonExceptionHandler() {
        return new CommonExceptionHandler();
    }
}
