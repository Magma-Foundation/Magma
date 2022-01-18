package com.meti.app.compile;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.CompiledTest.assertSourceCompile;

public class ReturnTest {
    @Test
    void returns() {
        assertSourceCompile("return 69", "return 69;");
    }
}
