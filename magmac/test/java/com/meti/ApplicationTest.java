package com.meti;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ApplicationTest {
    public static final String TEST_MAGMA_FUNCTION = "class def ApplicationTest() => {}";

    @Test
    void test() {
        Assertions.assertEquals(TEST_MAGMA_FUNCTION, TEST_MAGMA_FUNCTION);
    }
}