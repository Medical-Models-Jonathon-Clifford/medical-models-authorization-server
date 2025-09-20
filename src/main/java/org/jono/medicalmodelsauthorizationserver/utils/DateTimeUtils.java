package org.jono.medicalmodelsauthorizationserver.utils;

import java.time.Duration;

public class DateTimeUtils {

    private DateTimeUtils() {
        // Utility class
    }

    public static long parseTimeout(final String timeout) {
        if (timeout == null || timeout.isEmpty()) {
            throw new IllegalArgumentException("Timeout cannot be null or empty");
        }

        // Add the "PT" prefix if it's not a days-based duration
        final String isoDuration = timeout.toLowerCase().contains("d")
                ? "P" + timeout.toUpperCase()
                : "PT" + timeout.toUpperCase();

        return Duration.parse(isoDuration).toSeconds();
    }
}
