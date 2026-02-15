package dev.bored.common.security;

import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Helper methods for extracting claims from a decoded {@link Jwt}.
 * <p>
 * Null-safe — returns empty collections / defaults when claims are absent.
 * Works with both servlet and reactive security filters since it only
 * operates on the decoded token object.
 * </p>
 *
 * @author Bored Software Developer
 * @since 2026-02-15
 */
public final class JwtUtils {

    private JwtUtils() { }

    /**
     * Extracts role strings from the JWT's {@code roles} claim.
     * <p>
     * Supports the claim being a {@code List<String>} directly, or nested
     * inside a {@code realm_access.roles} structure (Keycloak-style).
     * </p>
     *
     * @param jwt the decoded JWT
     * @return a list of role strings, never null
     */
    @SuppressWarnings("unchecked")
    public static List<String> extractRoles(Jwt jwt) {
        if (jwt == null) return Collections.emptyList();

        // Direct "roles" claim (e.g. custom Google/GitHub token)
        Object roles = jwt.getClaim(JwtConstants.CLAIM_ROLES);
        if (roles instanceof Collection<?>) {
            return ((Collection<String>) roles).stream().toList();
        }

        // Keycloak-style nested claim
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess != null) {
            Object nested = realmAccess.get("roles");
            if (nested instanceof Collection<?>) {
                return ((Collection<String>) nested).stream().toList();
            }
        }

        return Collections.emptyList();
    }

    /**
     * Extracts the email claim from the JWT.
     *
     * @param jwt the decoded JWT
     * @return the email string, or {@code null} if absent
     */
    public static String extractEmail(Jwt jwt) {
        return jwt != null ? jwt.getClaimAsString(JwtConstants.CLAIM_EMAIL) : null;
    }

    /**
     * Extracts the subject (user ID) from the JWT.
     *
     * @param jwt the decoded JWT
     * @return the subject string, or {@code null} if absent
     */
    public static String extractSubject(Jwt jwt) {
        return jwt != null ? jwt.getSubject() : null;
    }

    /**
     * Checks whether the JWT contains the specified role.
     *
     * @param jwt  the decoded JWT
     * @param role the role to check for
     * @return {@code true} if the role is present
     */
    public static boolean hasRole(Jwt jwt, String role) {
        return extractRoles(jwt).contains(role);
    }
}
