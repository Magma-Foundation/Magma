package com.meti.app.compile;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.CompiledTest.assertSourceCompile;

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
