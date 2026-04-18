package dev.bored.common.web;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Configuration properties for shared CORS and web settings.
 * <p>
 * Bind with the prefix {@code bored} in {@code application.yml}:
 * <pre>
 * bored:
 *   cors:
 *     origins:
 *       - http://localhost:4200
 *       - https://boredsoftwaredeveloper.xyz
 *     allow-credentials: true
 *     max-age: 3600
 * </pre>
 * </p>
 *
 * @param cors the CORS settings
 * @author Bored Software Developer
 * @since 2026-04-18
 */
@ConfigurationProperties(prefix = "bored")
public record BoredProperties(Cors cors) {

    public BoredProperties {
        if (cors == null) {
            cors = Cors.defaults();
        }
    }

    /**
     * CORS policy settings shared across services.
     *
     * @param origins          the list of allowed origins
     * @param methods          the list of allowed HTTP methods
     * @param headers          the list of allowed request headers ({@code *} by default)
     * @param allowCredentials whether to allow credentials (cookies / auth headers)
     * @param maxAge           the pre-flight cache duration in seconds
     */
    public record Cors(
            List<String> origins,
            List<String> methods,
            List<String> headers,
            boolean allowCredentials,
            long maxAge
    ) {
        /** Reasonable defaults matching the existing hard-coded policy. */
        public static Cors defaults() {
            return new Cors(
                    List.of(
                            "http://localhost:4200",
                            "https://boredsoftwaredeveloper.xyz",
                            "https://www.boredsoftwaredeveloper.xyz"
                    ),
                    List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"),
                    List.of("*"),
                    true,
                    3600L
            );
        }

        /** Apply defaults for any null/empty collections. */
        public Cors withDefaults() {
            return new Cors(
                    origins == null || origins.isEmpty() ? defaults().origins() : origins,
                    methods == null || methods.isEmpty() ? defaults().methods() : methods,
                    headers == null || headers.isEmpty() ? defaults().headers() : headers,
                    allowCredentials,
                    maxAge <= 0 ? defaults().maxAge() : maxAge
            );
        }
    }
}
