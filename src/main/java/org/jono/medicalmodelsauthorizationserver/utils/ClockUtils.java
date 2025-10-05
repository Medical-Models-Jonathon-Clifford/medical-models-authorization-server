package org.jono.medicalmodelsauthorizationserver.utils;

import static org.jono.medicalmodelsauthorizationserver.utils.DateTimeUtils.parseTimeout;

import java.time.Clock;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClockUtils {

    private final Clock clock;

    public Instant calculateTimeoutFromNow(final String jwtTimeout) {
        final long jwtTimeoutSeconds = parseTimeout(jwtTimeout);
        return Instant.now(clock).plusSeconds(jwtTimeoutSeconds);
    }
}
