package dev.bored.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Lightweight JSON serialization/deserialization helper.
 * <p>
 * Uses a pre-configured {@link ObjectMapper} with Java-time support and
 * lenient unknown-property handling. For heavy use, prefer injecting
 * Spring's {@code ObjectMapper} bean instead.
 * </p>
 *
 * @author Bored Software Developer
 * @since 2026-02-15
 */
public final class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    private JsonUtils() { }

    /**
     * Serializes an object to a JSON string.
     *
     * @param obj the object to serialize
     * @return the JSON string
     * @throws IllegalArgumentException if serialization fails
     */
    public static String toJson(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to serialize to JSON", e);
        }
    }

    /**
     * Deserializes a JSON string to the specified type.
     *
     * @param json  the JSON string
     * @param clazz the target class
     * @param <T>   the target type
     * @return the deserialized object
     * @throws IllegalArgumentException if deserialization fails
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to deserialize JSON", e);
        }
    }

    /**
     * Returns the shared, pre-configured {@link ObjectMapper}.
     *
     * @return the object mapper instance
     */
    public static ObjectMapper mapper() {
        return MAPPER;
    }
}
