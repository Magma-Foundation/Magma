package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    private static String compile(String input) {
        return "";
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "\t", "\n"})
    void whitespace(String input) {
        assertEquals("", compile(input));
    }

    @Test
    void empty() {
        assertEquals("", compile(""));
    }
}
