package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.assertCompile;

public class ReturnTest {
    @Test
    void returns() {
        assertCompile("return 69", "return 69;");
    }
}
