package com.meti;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    private static Option run(String value) {
        try {
            Integer.parseInt(value);
            return new Some(value);
        } catch (NumberFormatException e) {
            return new None();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"5", "10"})
    void integer(String value) {
        assertEquals(value, run(value).unwrapOrPanic());
    }
}
