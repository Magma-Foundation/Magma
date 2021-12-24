package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.assertCompile;

public class ReturnTest {
    @Test
    void valid() {
        assertCompile("return 420", "return 420;");
    }
}
