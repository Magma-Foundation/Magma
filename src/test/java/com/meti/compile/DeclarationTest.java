package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.assertSourceCompile;

public class DeclarationTest {
    @Test
    void simple() {
        assertSourceCompile("const x : I16 = 420", "int x=420;");
    }
}
