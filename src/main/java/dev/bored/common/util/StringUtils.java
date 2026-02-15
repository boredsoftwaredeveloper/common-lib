package dev.bored.common.util;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * String parsing and transformation utilities.
 * <p>
 * Null-safe by convention — methods that accept a {@code String} return
 * {@code null} (or a sensible default) when the input is {@code null}.
 * </p>
 *
 * @author Bored Software Developer
 * @since 2026-02-15
 */
public final class StringUtils {

    private static final Pattern NON_ALPHA_NUM = Pattern.compile("[^a-z0-9]+");
    private static final Pattern LEADING_TRAILING_DASH = Pattern.compile("^-|-$");

    private StringUtils() { }

    /**
     * Converts a string to a URL-friendly slug.
     * <p>
     * Example: {@code "Deloitte Consulting LLC"} → {@code "deloitte-consulting-llc"}
     * </p>
     *
     * @param text the input string
     * @return the slugified string, or {@code null} if text is null
     */
    public static String slugify(String text) {
        if (text == null) return null;
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        String slug = NON_ALPHA_NUM.matcher(normalized.toLowerCase(Locale.ENGLISH)).replaceAll("-");
        return LEADING_TRAILING_DASH.matcher(slug).replaceAll("");
    }

    /**
     * Truncates a string to the specified max length, appending an ellipsis
     * if truncation occurs.
     *
     * @param text      the input string
     * @param maxLength the maximum length (must be ≥ 3)
     * @return the truncated string, or the original if it's within limits
     * @throws IllegalArgumentException if maxLength &lt; 3
     */
    public static String truncate(String text, int maxLength) {
        if (maxLength < 3) {
            throw new IllegalArgumentException("maxLength must be at least 3");
        }
        if (text == null || text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "...";
    }

    /**
     * Returns {@code true} if the string is null, empty, or contains only whitespace.
     *
     * @param text the string to check
     * @return whether the string is blank
     */
    public static boolean isBlank(String text) {
        return text == null || text.isBlank();
    }

    /**
     * Returns {@code true} if the string is non-null and contains at least one
     * non-whitespace character.
     *
     * @param text the string to check
     * @return whether the string has content
     */
    public static boolean isNotBlank(String text) {
        return !isBlank(text);
    }

    /**
     * Capitalizes the first letter of each word in the string.
     * <p>
     * Example: {@code "bored software developer"} → {@code "Bored Software Developer"}
     * </p>
     *
     * @param text the input string
     * @return the title-cased string, or {@code null} if text is null
     */
    public static String toTitleCase(String text) {
        if (text == null) return null;
        if (text.isBlank()) return text;
        String[] words = text.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (!sb.isEmpty()) sb.append(' ');
            sb.append(Character.toUpperCase(word.charAt(0)));
            if (word.length() > 1) sb.append(word.substring(1).toLowerCase(Locale.ENGLISH));
        }
        return sb.toString();
    }

    /**
     * Null-safe trim. Returns {@code null} if the input is null.
     *
     * @param text the string to trim
     * @return the trimmed string
     */
    public static String trimSafe(String text) {
        return text != null ? text.trim() : null;
    }

    /**
     * Returns a default value if the input is blank.
     *
     * @param text         the string to check
     * @param defaultValue the fallback value
     * @return the original string if not blank, otherwise the default
     */
    public static String defaultIfBlank(String text, String defaultValue) {
        return isBlank(text) ? defaultValue : text;
    }
}
