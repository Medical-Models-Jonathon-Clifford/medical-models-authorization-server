package org.jono.medicalmodelsauthorizationserver.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import org.junit.jupiter.api.Test;

class ClockUtilsTest {

    @Test
    void shouldAddCorrectSeconds() {
        final Instant fixedInstant = Instant.parse("2025-01-01T10:00:00Z");
        final Clock fixedClock = Clock.fixed(fixedInstant, ZoneId.systemDefault());
        final ClockUtils clockUtils = new ClockUtils(fixedClock);

        final Instant result = clockUtils.calculateTimeoutFromNow("30s");

        final Instant expected = Instant.parse("2025-01-01T10:00:30Z");
        assertEquals(expected, result);
    }
}
