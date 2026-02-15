package dev.bored.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Date parsing and formatting utilities.
 * <p>
 * Tries multiple common formats when parsing so callers don't have to
 * guess which pattern the input uses. Thread-safe — all formatters are
 * immutable.
 * </p>
 *
 * @author Bored Software Developer
 * @since 2026-02-15
 */
public final class DateUtils {

    /** ISO date: {@code 2026-02-15} */
    public static final DateTimeFormatter ISO = DateTimeFormatter.ISO_LOCAL_DATE;

    /** US date: {@code 02/15/2026} */
    public static final DateTimeFormatter US = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    /** EU date: {@code 15-02-2026} */
    public static final DateTimeFormatter EU = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /** Month-year: {@code Feb 2026} */
    public static final DateTimeFormatter MONTH_YEAR = DateTimeFormatter.ofPattern("MMM yyyy");

    /** ISO date-time: {@code 2026-02-15T10:30:00} */
    public static final DateTimeFormatter ISO_DT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private static final List<DateTimeFormatter> DATE_PARSERS = List.of(ISO, US, EU);

    private DateUtils() { }

    /**
     * Parses a date string by trying ISO, US, and EU formats in order.
     *
     * @param text the date string to parse
     * @return the parsed {@link LocalDate}
     * @throws DateTimeParseException if none of the formats match
     * @throws IllegalArgumentException if text is null or blank
     */
    public static LocalDate parseDate(String text) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Date text must not be null or blank");
        }
        String trimmed = text.trim();
        for (DateTimeFormatter fmt : DATE_PARSERS) {
            try {
                return LocalDate.parse(trimmed, fmt);
            } catch (DateTimeParseException ignored) {
                // try next format
            }
        }
        throw new DateTimeParseException("Unable to parse date: " + trimmed, trimmed, 0);
    }

    /**
     * Parses an ISO date-time string ({@code yyyy-MM-ddTHH:mm:ss}).
     *
     * @param text the date-time string
     * @return the parsed {@link LocalDateTime}
     * @throws DateTimeParseException if the format doesn't match
     * @throws IllegalArgumentException if text is null or blank
     */
    public static LocalDateTime parseDateTime(String text) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("DateTime text must not be null or blank");
        }
        return LocalDateTime.parse(text.trim(), ISO_DT);
    }

    /**
     * Formats a {@link LocalDate} to ISO format ({@code yyyy-MM-dd}).
     *
     * @param date the date to format
     * @return the formatted string, or {@code null} if date is null
     */
    public static String formatIso(LocalDate date) {
        return date != null ? date.format(ISO) : null;
    }

    /**
     * Formats a {@link LocalDateTime} to ISO format.
     *
     * @param dateTime the date-time to format
     * @return the formatted string, or {@code null} if dateTime is null
     */
    public static String formatIso(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(ISO_DT) : null;
    }

    /**
     * Formats a {@link LocalDate} using the given formatter.
     *
     * @param date      the date to format
     * @param formatter the formatter to use
     * @return the formatted string, or {@code null} if date is null
     */
    public static String format(LocalDate date, DateTimeFormatter formatter) {
        return date != null ? date.format(formatter) : null;
    }
}
