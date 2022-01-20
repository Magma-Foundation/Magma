package com.meti.app.compile.operate;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.CompiledTest.assertSourceCompile;

public class BinaryTest {
    @Test
    void inequality() {
        assertSourceCompile("x != y", "x!=y");
    }

    @Test
    void test() {
        assertSourceCompile("{x = 10}", "{x=10;}");
    }
}
