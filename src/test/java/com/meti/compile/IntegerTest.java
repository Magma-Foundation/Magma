package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.assertCompile;

public class IntegerTest {
    @Test
    void integer() {
        assertCompile("420", "420");
    }
}
