package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilerTest {
    @Test
    void name() {
        assertCompile(new MagmaFunctionRenderer().render("empty", "Void", "{}"), new CFunctionRenderer().render("empty", "void", "{}"));
    }

    private void assertCompile(String input, String expected) {
        var compiler = new Compiler(input);
        var output = compiler.compile();
        assertEquals(expected, output);
    }

    @Test
    void type() {
        assertCompile(new MagmaFunctionRenderer().render("supplier", "U16", "{return 420;}"), new CFunctionRenderer().render("supplier", "unsigned int", "{return 420;}"));
    }

    @Test
    void body() {
        assertCompile(new MagmaFunctionRenderer().render("supplier", "I16", "{return 420;}"), new CFunctionRenderer().render("supplier", "int", "{return 420;}"));
    }
}