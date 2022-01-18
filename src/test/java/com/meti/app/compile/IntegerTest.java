package com.meti.app.compile;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.CompiledTest.assertSourceCompile;

public class IntegerTest {
    @Test
    void integer() {
        assertSourceCompile("420", "420");
    }
}
