package com.meti.app.compile.function;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.CompiledTest.assertSourceCompile;

public class BinaryTest {
    @Test
    void inequality() {
        assertSourceCompile("const x = 420; const y = 69; x != y;",
                "int x=420;int y=69;x != y;");
    }

    @Test
    void test() {
        assertSourceCompile("{x = 10}", "{x = 10;}");
    }
}
