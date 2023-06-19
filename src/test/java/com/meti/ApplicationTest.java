package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    private static String compile(String input) {
        try {
            Integer.parseInt(input);
            return input;
        } catch (NumberFormatException e) {
            return "";
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "1", "2"})
    void integer(String input) {
        assertInterpret(input, input);
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "\t", "\n"})
    void whitespace(String input) {
        assertInterpret(input, "");
    }

    private static void assertInterpret(String input, String output) {
        assertEquals(output, compile(input));
    }

    @Test
    void empty() {
        assertInterpret("", "");
    }
}
