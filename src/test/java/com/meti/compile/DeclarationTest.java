package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.assertCompile;
import static com.meti.compile.CompiledTest.assertDoesNotCompile;

public class DeclarationTest {
    @Test
    void mutable() {
        assertCompile("let x = 420;", "int x=420;");
    }

    @Test
    void redeclare() {
        assertDoesNotCompile("const x = 420;const x=69;");
    }

    @Test
    void implicit() {
        assertCompile("const x = 420;", "int x=420;");
    }

    @Test
    void name() {
        assertCompile("const test : I16 = 420;", "int test=420;");
    }

    @Test
    void simple() {
        assertCompile("const x : I16 = 420;", "int x=420;");
    }

    @Test
    void type() {
        assertCompile("const x : U16 = 420;", "unsigned int x=420;");
    }

    @Test
    void value() {
        assertCompile("const x : I16 = 69;", "int x=69;");
    }
}
