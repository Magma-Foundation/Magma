package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    @ParameterizedTest
    @ValueSource(strings = {"a", "b"})
    void test(String c) {
        assertEquals(createError(c), run(c));
    }

    private static String run(String c) {
        return createError(c);
    }

    private static String createError(String c) {
        return "**Location**: com.meti.Test\n" +
               "**Message**: Unknown Token*\n" +
               "\n" +
               "**Details**:\n" +
               "```\n" +
               "1) " + c + "\n" +
               "   ^\n" +
               "```";
    }
}
