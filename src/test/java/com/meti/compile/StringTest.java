package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.assertSourceCompile;

public class StringTest {
    @Test
    void value() {
        assertSourceCompile("\"Hello World!\"", "\"Hello World!\"");
    }
}
