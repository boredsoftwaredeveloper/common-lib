package dev.bored.common.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.*;

class DateUtilsTest {

    // ── parseDate ─────────────────────────────────────────────

    @ParameterizedTest
    @CsvSource({
            "2026-02-15, 2026-02-15",   // ISO
            "02/15/2026, 2026-02-15",   // US
            "15-02-2026, 2026-02-15",   // EU
    })
    void parseDate_parsesMultipleFormats(String input, String expected) {
        assertThat(DateUtils.parseDate(input)).isEqualTo(LocalDate.parse(expected));
    }

    @Test
    void parseDate_trimsWhitespace() {
        assertThat(DateUtils.parseDate("  2026-02-15  ")).isEqualTo(LocalDate.of(2026, 2, 15));
    }

    @Test
    void parseDate_throwsOnInvalidFormat() {
        assertThatThrownBy(() -> DateUtils.parseDate("not-a-date"))
                .isInstanceOf(DateTimeParseException.class);
    }

    @Test
    void parseDate_throwsOnNull() {
        assertThatThrownBy(() -> DateUtils.parseDate(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void parseDate_throwsOnBlank() {
        assertThatThrownBy(() -> DateUtils.parseDate("   "))
                .isInstanceOf(IllegalArgumentException.class);
    }

    // ── parseDateTime ─────────────────────────────────────────

    @Test
    void parseDateTime_parsesIsoFormat() {
        LocalDateTime result = DateUtils.parseDateTime("2026-02-15T10:30:00");
        assertThat(result).isEqualTo(LocalDateTime.of(2026, 2, 15, 10, 30, 0));
    }

    @Test
    void parseDateTime_throwsOnNull() {
        assertThatThrownBy(() -> DateUtils.parseDateTime(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void parseDateTime_throwsOnBlank() {
        assertThatThrownBy(() -> DateUtils.parseDateTime(""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    // ── formatIso ─────────────────────────────────────────────

    @Test
    void formatIso_formatsLocalDate() {
        assertThat(DateUtils.formatIso(LocalDate.of(2026, 2, 15))).isEqualTo("2026-02-15");
    }

    @Test
    void formatIso_returnsNullForNullDate() {
        assertThat(DateUtils.formatIso((LocalDate) null)).isNull();
    }

    @Test
    void formatIso_formatsLocalDateTime() {
        assertThat(DateUtils.formatIso(LocalDateTime.of(2026, 2, 15, 10, 30)))
                .isEqualTo("2026-02-15T10:30:00");
    }

    @Test
    void formatIso_returnsNullForNullDateTime() {
        assertThat(DateUtils.formatIso((LocalDateTime) null)).isNull();
    }

    // ── format with custom formatter ──────────────────────────

    @Test
    void format_usesCustomFormatter() {
        String result = DateUtils.format(LocalDate.of(2026, 2, 15), DateUtils.US);
        assertThat(result).isEqualTo("02/15/2026");
    }

    @Test
    void format_returnsNullForNullDate() {
        assertThat(DateUtils.format(null, DateUtils.US)).isNull();
    }
}
