package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    public static final String MAGMA_FUNCTION = "class def ApplicationTest() => {}";

    @Test
    void test() {
        assertEquals(MAGMA_FUNCTION, MAGMA_FUNCTION);
    }
}