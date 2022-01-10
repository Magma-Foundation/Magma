package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.assertSourceCompile;

public class IntegerTest {
    @Test
    void integer() {
        assertSourceCompile("420", "420");
    }
}
