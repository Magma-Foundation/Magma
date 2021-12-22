package com.meti;

import org.junit.jupiter.api.Test;

import static com.meti.CompiledTest.assertCompile;

public class ReturnTest {
    @Test
    void valid() {
        assertCompile("return 420", "return 420;");
    }
}
