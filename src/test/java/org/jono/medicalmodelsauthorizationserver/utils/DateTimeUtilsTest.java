package org.jono.medicalmodelsauthorizationserver.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.argumentSet;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DateTimeUtilsTest {

    @ParameterizedTest
    @MethodSource("provideTimeouts")
    void shouldParseTimeBasedTimeouts(final String timeout, final long expectedSeconds) {
        final long result = DateTimeUtils.parseTimeout(timeout);
        assertThat(result).isEqualTo(expectedSeconds);
    }

    private static Stream<Arguments> provideTimeouts() {
        return Stream.of(
                argumentSet("Seconds lowercase", "30s", 30L),
                argumentSet("Seconds longer than a minute", "120s", 120L),
                argumentSet("Minutes lowercase", "5m", 300L),
                argumentSet("Minutes longer than an hour", "90m", 5400L),
                argumentSet("Hours lowercase", "2h", 7200L),
                argumentSet("Days lowercase", "1d", 86400L),
                argumentSet("Negative seconds", "-10s", -10L),
                argumentSet("Negative minutes", "-7m", -420L),
                argumentSet("Negative days", "-21d", -1814400L),
                argumentSet("Minutes and seconds", "4m30s", 270L),
                argumentSet("Hours and minutes", "3h15m", 11700L),
                argumentSet("Seconds uppercase", "10S", 10L),
                argumentSet("Minutes uppercase", "5M", 300L),
                argumentSet("Hours uppercase", "2H", 7200L),
                argumentSet("Days uppercase", "1D", 86400L),
                argumentSet("Zero seconds", "0s", 0L),
                argumentSet("Zero minutes", "0m", 0L),
                argumentSet("Zero hours", "0h", 0L),
                argumentSet("Zero days", "0d", 0L)
        );
    }

    @ParameterizedTest
    @MethodSource("provideIllegalArguments")
    void shouldThrowExceptionForIllegalArguments(final String timeout) {
        final IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> DateTimeUtils.parseTimeout(timeout)
        );
        assertThat(exception.getMessage()).isEqualTo("Timeout cannot be null or empty");
    }

    private static Stream<Arguments> provideIllegalArguments() {
        return Stream.of(
                argumentSet("Null", (String) null),
                argumentSet("Empty string", "")
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidArguments")
    void shouldThrowExceptionForInvalidFormat(final String timeout) {
        final Exception exception = assertThrows(
                Exception.class,
                () -> DateTimeUtils.parseTimeout(timeout)
        );
        assertThat(exception).isInstanceOf(Exception.class);
    }

    private static Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                argumentSet("Invalid format", "invalid"),
                argumentSet("Invalid time unit", "10x")
        );
    }
}
