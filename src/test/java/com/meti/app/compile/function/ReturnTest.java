package com.meti.app.compile.function;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.CompiledTest.assertDoesNotCompile;
import static com.meti.app.compile.CompiledTest.assertSourceCompile;

public class ReturnTest {
    @Test
    void return_undefined() {
        assertDoesNotCompile("def empty() => {return x;}");
    }

    @Test
    void returns() {
        assertSourceCompile("return 69", "return 69;");
    }
}
