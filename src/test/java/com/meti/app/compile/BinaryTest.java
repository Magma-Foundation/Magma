package com.meti.app.compile;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.CompiledTest.assertSourceCompile;

public class BinaryTest {
    @Test
    void test() {
        assertSourceCompile("{x = 10}", "{x=10;}");
    }
}
