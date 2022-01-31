package com.meti.app.compile.scope;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.CompiledTest.assertDoesNotCompile;
import static com.meti.app.compile.CompiledTest.assertSourceCompile;

public class DeclarationTest {
    @Test
    void flag_conflict() {
        assertDoesNotCompile("const let x = 420");
    }

    @Test
    void flag_duplicate() {
        assertDoesNotCompile("const const x = 420");
    }

    @Test
    void name_duplicate() {
        assertDoesNotCompile("const x : I16=420;const x : U16=420;");
    }

    @Test
    void nominal() {
        assertSourceCompile("const x : I16 = 420", "int x=420;");
    }

    @Test
    void type_conflict() {
        assertDoesNotCompile("const x : I16 = true");
    }

    @Test
    void type_none() {
        assertSourceCompile("const x = 420", "int x=420;");
    }

    @Test
    void value_none() {
        assertSourceCompile("let x : I16", "int x;");
    }
}
