package com.meti.app.compile.scope;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.CompiledTest.assertDoesNotCompile;
import static com.meti.app.compile.CompiledTest.assertSourceCompile;

public class DeclarationTest {
    @Test
    void conflicting_flags() {
        assertDoesNotCompile("const let x = 420");
    }

    @Test
    void duplicate_flags() {
        assertDoesNotCompile("const const x = 420");
    }

    @Test
    void empty() {
        assertSourceCompile("let x : I16", "int x;");
    }

    @Test
    void implicit() {
        assertSourceCompile("const x = 420", "int x=420;");
    }

    @Test
    void repeated() {
        assertDoesNotCompile("const x : I16=420;const x : U16=420;");
    }

    @Test
    void simple() {
        assertSourceCompile("const x : I16 = 420", "int x=420;");
    }
}
