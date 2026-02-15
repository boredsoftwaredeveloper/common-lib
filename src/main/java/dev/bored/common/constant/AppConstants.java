package dev.bored.common.constant;

/**
 * Shared constants used across all microservices.
 *
 * @author Bored Software Developer
 * @since 2026-02-15
 */
public final class AppConstants {

    private AppConstants() { }

    // ── API versioning ────────────────────────────────────────
    /** Base path prefix for all versioned API endpoints. */
    public static final String API_V1 = "/api/v1";

    // ── CORS origins ──────────────────────────────────────────
    /** Local Angular dev server. */
    public static final String ORIGIN_LOCAL = "http://localhost:4200";

    /** Production domain. */
    public static final String ORIGIN_PROD = "https://boredsoftwaredeveloper.xyz";

    /** Production domain (www). */
    public static final String ORIGIN_PROD_WWW = "https://www.boredsoftwaredeveloper.xyz";

    /** All allowed origins as an array. */
    public static final String[] ALLOWED_ORIGINS = {
            ORIGIN_LOCAL, ORIGIN_PROD, ORIGIN_PROD_WWW
    };

    /** All allowed HTTP methods for CORS. */
    public static final String[] ALLOWED_METHODS = {
            "GET", "POST", "PUT", "DELETE", "OPTIONS"
    };

    /** CORS pre-flight cache duration in seconds (1 hour). */
    public static final long CORS_MAX_AGE = 3600L;

    // ── Security roles ────────────────────────────────────────
    /** Admin role name used in {@code @PreAuthorize}. */
    public static final String ROLE_ADMIN = "ADMIN";

    // ── Pagination defaults ───────────────────────────────────
    /** Default page number. */
    public static final int DEFAULT_PAGE = 0;

    /** Default page size. */
    public static final int DEFAULT_PAGE_SIZE = 20;

    /** Maximum page size to prevent abuse. */
    public static final int MAX_PAGE_SIZE = 100;
}
