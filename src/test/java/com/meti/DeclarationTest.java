package com.meti;

import org.junit.jupiter.api.Test;

import static com.meti.CompiledTest.assertCompile;

public class DeclarationTest {
    @Test
    void simple() {
        assertCompile("const x : I16 = 420;", "int x=420;");
    }

    @Test
    void name() {
        assertCompile("const test : I16 = 420;", "int test=420;");
    }
}
