package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.assertSourceCompile;

public class ReturnTest {
    @Test
    void returns() {
        assertSourceCompile("return 69", "return 69;");
    }
}
