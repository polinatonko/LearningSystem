package org.example.learningsystem.core.i18n;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static java.util.Objects.nonNull;

/**
 * Formats {@link LocalDateTime} objects into human-readable form using provided {@link Locale}.
 */
@Component
public class LocalizedEmailDateTimeFormatter {

    private static final String EMPTY_DATE = "*";
    private static final String DATE_TIME_FORMAT = "LLLL d h:mm a";

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    /**
     * Formats {@link LocalDateTime} instance for the given {@link Locale}.
     *
     * @param date   the {@link LocalDateTime} instance to format
     * @param locale the {@link Locale} to use
     * @return the formatted date or {@value #EMPTY_DATE} if {@code date} is {@code null}
     */
    public String format(LocalDateTime date, Locale locale) {
        return nonNull(date)
                ? date.format(formatter.withLocale(locale))
                : EMPTY_DATE;
    }

}
