package dev.bored.common.security;

/**
 * JWT-related constants shared across services.
 *
 * @author Bored Software Developer
 * @since 2026-02-15
 */
public final class JwtConstants {

    private JwtConstants() { }

    /** Standard Authorization header name. */
    public static final String AUTH_HEADER = "Authorization";

    /** Bearer token prefix. */
    public static final String BEARER_PREFIX = "Bearer ";

    /** JWT claim key for roles. */
    public static final String CLAIM_ROLES = "roles";

    /** JWT claim key for email. */
    public static final String CLAIM_EMAIL = "email";

    /** JWT claim key for name. */
    public static final String CLAIM_NAME = "name";
}
