package com.meti;

import org.junit.jupiter.api.Test;

import static com.meti.CompiledTest.assertCompile;

public class FunctionTest {
    @Test
    void natives() {
        assertCompile("native def empty() : Void", "");
    }
}
