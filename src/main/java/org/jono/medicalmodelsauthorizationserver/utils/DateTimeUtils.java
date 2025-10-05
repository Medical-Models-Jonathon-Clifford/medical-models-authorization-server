package org.jono.medicalmodelsauthorizationserver.utils;

import java.time.Duration;

public final class DateTimeUtils {

    private DateTimeUtils() {
        // Utility class
    }

    public static long parseTimeout(final String timeout) {
        validateTimeout(timeout);
        return Duration.parse(toIsoDuration(timeout)).toSeconds();
    }

    private static void validateTimeout(final String timeout) {
        if (timeout == null || timeout.isEmpty()) {
            throw new IllegalArgumentException("Timeout cannot be null or empty");
        }
    }

    private static String toIsoDuration(final String timeout) {
        return timeout.toLowerCase().contains("d")
                ? "P" + timeout.toUpperCase()
                : "PT" + timeout.toUpperCase();
    }
}
