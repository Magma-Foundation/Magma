package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.assertCompile;

public class FunctionTest {
    @Test
    void natives() {
        assertCompile("native def empty() : Void", "");
    }
}
