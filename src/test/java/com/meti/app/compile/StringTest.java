package com.meti.app.compile;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.CompiledTest.assertSourceCompile;

public class StringTest {
    @Test
    void value() {
        assertSourceCompile("\"Hello World!\"", "\"Hello World!\"");
    }

    @Test
    void whitespace() {
        assertSourceCompile("\" test \"", "\" test \"");
    }
}
