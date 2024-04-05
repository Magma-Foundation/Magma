package com.meti;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {

    private static String getString(String c) {
        return "**Location**: com.meti.Test\n" +
               "**Message**: Unknown Token*\n" +
               "\n" +
               "**Details**:\n" +
               "```\n" +
               "1) " + c + "\n" +
               "   ^\n" +
               "```";
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "b"})
    void testOneChar(String c) {
        assertEquals(getString(c), getString(c));
    }
}
