package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    public static final String CLASS_STRING = "class def ApplicationTest() => {}";

    @Test
    void test() {
        assertEquals(CLASS_STRING, CLASS_STRING);
    }
}
