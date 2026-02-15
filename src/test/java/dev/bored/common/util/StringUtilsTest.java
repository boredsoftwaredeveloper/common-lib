package dev.bored.common.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.*;

class StringUtilsTest {

    // ── slugify ───────────────────────────────────────────────

    @ParameterizedTest
    @CsvSource({
            "Deloitte Consulting LLC, deloitte-consulting-llc",
            "Hello World!, hello-world",
            "café-résumé, cafe-resume",
            "  leading spaces  , leading-spaces",
            "UPPER CASE, upper-case",
    })
    void slugify_convertsToUrlFriendlySlug(String input, String expected) {
        assertThat(StringUtils.slugify(input)).isEqualTo(expected);
    }

    @Test
    void slugify_returnsNullForNull() {
        assertThat(StringUtils.slugify(null)).isNull();
    }

    // ── truncate ──────────────────────────────────────────────

    @Test
    void truncate_shortStringUnchanged() {
        assertThat(StringUtils.truncate("hello", 10)).isEqualTo("hello");
    }

    @Test
    void truncate_longStringTruncatedWithEllipsis() {
        assertThat(StringUtils.truncate("hello world", 8)).isEqualTo("hello...");
    }

    @Test
    void truncate_returnsNullForNull() {
        assertThat(StringUtils.truncate(null, 10)).isNull();
    }

    @Test
    void truncate_throwsIfMaxLengthTooSmall() {
        assertThatThrownBy(() -> StringUtils.truncate("hi", 2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void truncate_exactLengthUnchanged() {
        assertThat(StringUtils.truncate("abc", 3)).isEqualTo("abc");
    }

    // ── isBlank / isNotBlank ──────────────────────────────────

    @ParameterizedTest
    @NullSource
    void isBlank_trueForNull(String input) {
        assertThat(StringUtils.isBlank(input)).isTrue();
    }

    @Test
    void isBlank_trueForEmpty() {
        assertThat(StringUtils.isBlank("")).isTrue();
    }

    @Test
    void isBlank_trueForWhitespace() {
        assertThat(StringUtils.isBlank("   ")).isTrue();
    }

    @Test
    void isBlank_falseForContent() {
        assertThat(StringUtils.isBlank("hello")).isFalse();
    }

    @Test
    void isNotBlank_trueForContent() {
        assertThat(StringUtils.isNotBlank("hello")).isTrue();
    }

    @Test
    void isNotBlank_falseForNull() {
        assertThat(StringUtils.isNotBlank(null)).isFalse();
    }

    // ── toTitleCase ───────────────────────────────────────────

    @ParameterizedTest
    @CsvSource({
            "bored software developer, Bored Software Developer",
            "HELLO WORLD, Hello World",
            "a, A",
    })
    void toTitleCase_capitalizesEachWord(String input, String expected) {
        assertThat(StringUtils.toTitleCase(input)).isEqualTo(expected);
    }

    @Test
    void toTitleCase_returnsNullForNull() {
        assertThat(StringUtils.toTitleCase(null)).isNull();
    }

    @Test
    void toTitleCase_returnsBlankForBlank() {
        assertThat(StringUtils.toTitleCase("   ")).isEqualTo("   ");
    }

    // ── trimSafe ──────────────────────────────────────────────

    @Test
    void trimSafe_trimsWhitespace() {
        assertThat(StringUtils.trimSafe("  hello  ")).isEqualTo("hello");
    }

    @Test
    void trimSafe_returnsNullForNull() {
        assertThat(StringUtils.trimSafe(null)).isNull();
    }

    // ── defaultIfBlank ────────────────────────────────────────

    @Test
    void defaultIfBlank_returnsValueWhenNotBlank() {
        assertThat(StringUtils.defaultIfBlank("hello", "fallback")).isEqualTo("hello");
    }

    @Test
    void defaultIfBlank_returnsDefaultWhenBlank() {
        assertThat(StringUtils.defaultIfBlank("", "fallback")).isEqualTo("fallback");
    }

    @Test
    void defaultIfBlank_returnsDefaultWhenNull() {
        assertThat(StringUtils.defaultIfBlank(null, "fallback")).isEqualTo("fallback");
    }
}
