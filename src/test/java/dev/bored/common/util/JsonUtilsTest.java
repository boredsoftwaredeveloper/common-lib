package dev.bored.common.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class JsonUtilsTest {

    // ── toJson ────────────────────────────────────────────────

    @Test
    void toJson_serializesMap() {
        String json = JsonUtils.toJson(Map.of("name", "bored"));
        assertThat(json).contains("\"name\"").contains("\"bored\"");
    }

    @Test
    void toJson_serializesDatesAsStrings() {
        record DateHolder(LocalDate date) {}
        String json = JsonUtils.toJson(new DateHolder(LocalDate.of(2026, 2, 15)));
        assertThat(json).contains("\"2026-02-15\"");
    }

    @Test
    void toJson_throwsOnUnserializable() {
        // Create a self-referencing object that Jackson cannot serialize
        Object[] selfRef = new Object[1];
        selfRef[0] = selfRef;
        assertThatThrownBy(() -> JsonUtils.toJson(selfRef))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Failed to serialize");
    }

    @Test
    void toJson_serializesSimpleString() {
        assertThat(JsonUtils.toJson("simple")).isEqualTo("\"simple\"");
    }

    // ── fromJson ──────────────────────────────────────────────

    @Test
    void fromJson_deserializesString() {
        String result = JsonUtils.fromJson("\"hello\"", String.class);
        assertThat(result).isEqualTo("hello");
    }

    @Test
    void fromJson_deserializesMap() {
        @SuppressWarnings("unchecked")
        Map<String, String> result = JsonUtils.fromJson("{\"key\":\"value\"}", Map.class);
        assertThat(result).containsEntry("key", "value");
    }

    @Test
    void fromJson_throwsOnInvalidJson() {
        assertThatThrownBy(() -> JsonUtils.fromJson("not-json", Map.class))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void fromJson_ignoresUnknownProperties() {
        record Simple(String name) {}
        Simple result = JsonUtils.fromJson("{\"name\":\"bored\",\"extra\":true}", Simple.class);
        assertThat(result.name()).isEqualTo("bored");
    }

    // ── mapper ────────────────────────────────────────────────

    @Test
    void mapper_returnsNonNull() {
        assertThat(JsonUtils.mapper()).isNotNull();
    }
}
