package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.assertCompile;
import static com.meti.compile.CompiledTest.assertDoesNotCompile;

public class ReturnTest {
    @Test
    void valid() {
        assertCompile("return 420", "return 420;");
    }

    @Test
    void invalid() {
        assertDoesNotCompile("return return 69");
    }
}
