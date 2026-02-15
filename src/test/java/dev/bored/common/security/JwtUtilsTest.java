package dev.bored.common.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class JwtUtilsTest {

    private Jwt buildJwt(Map<String, Object> claims) {
        Jwt.Builder builder = Jwt.withTokenValue("token")
                .header("alg", "RS256")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .subject("user-123");
        claims.forEach(builder::claim);
        return builder.build();
    }

    // ── extractRoles ──────────────────────────────────────────

    @Test
    void extractRoles_fromDirectClaim() {
        Jwt jwt = buildJwt(Map.of("roles", List.of("ADMIN", "USER")));
        assertThat(JwtUtils.extractRoles(jwt)).containsExactly("ADMIN", "USER");
    }

    @Test
    void extractRoles_fromKeycloakRealmAccess() {
        Jwt jwt = buildJwt(Map.of("realm_access", Map.of("roles", List.of("ADMIN"))));
        assertThat(JwtUtils.extractRoles(jwt)).containsExactly("ADMIN");
    }

    @Test
    void extractRoles_emptyWhenNoRolesClaim() {
        Jwt jwt = buildJwt(Map.of("email", "test@test.com"));
        assertThat(JwtUtils.extractRoles(jwt)).isEmpty();
    }

    @Test
    void extractRoles_returnsEmptyForNull() {
        assertThat(JwtUtils.extractRoles(null)).isEmpty();
    }

    // ── extractEmail ──────────────────────────────────────────

    @Test
    void extractEmail_returnsEmail() {
        Jwt jwt = buildJwt(Map.of("email", "bored@dev.xyz"));
        assertThat(JwtUtils.extractEmail(jwt)).isEqualTo("bored@dev.xyz");
    }

    @Test
    void extractEmail_returnsNullWhenAbsent() {
        Jwt jwt = buildJwt(Map.of());
        assertThat(JwtUtils.extractEmail(jwt)).isNull();
    }

    @Test
    void extractEmail_returnsNullForNullJwt() {
        assertThat(JwtUtils.extractEmail(null)).isNull();
    }

    // ── extractSubject ────────────────────────────────────────

    @Test
    void extractSubject_returnsSubject() {
        Jwt jwt = buildJwt(Map.of());
        assertThat(JwtUtils.extractSubject(jwt)).isEqualTo("user-123");
    }

    @Test
    void extractSubject_returnsNullForNullJwt() {
        assertThat(JwtUtils.extractSubject(null)).isNull();
    }

    // ── hasRole ───────────────────────────────────────────────

    @Test
    void hasRole_trueWhenPresent() {
        Jwt jwt = buildJwt(Map.of("roles", List.of("ADMIN")));
        assertThat(JwtUtils.hasRole(jwt, "ADMIN")).isTrue();
    }

    @Test
    void hasRole_falseWhenAbsent() {
        Jwt jwt = buildJwt(Map.of("roles", List.of("USER")));
        assertThat(JwtUtils.hasRole(jwt, "ADMIN")).isFalse();
    }
}
