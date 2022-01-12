package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.assertSourceCompile;

public class DeclarationTest {
    @Test
    void empty(){
        assertSourceCompile("let x : I16", "int x;");
    }

    @Test
    void simple() {
        assertSourceCompile("const x : I16 = 420", "int x=420;");
    }
}
