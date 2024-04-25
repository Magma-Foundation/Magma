package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {

    public static final String TEST_FUNCTION = "class def ApplicationTest() => {}";

    @Test
    void test() {
        assertEquals(TEST_FUNCTION, run());
    }

    private static String run() {
        return TEST_FUNCTION;
    }
}
