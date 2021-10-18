package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilerTest {

    @Test
    void name() {
        var body = new BlockRenderer().render("");
        var input = new MagmaFunctionRenderer().render("empty", "Void", body);
        var output = new CFunctionRenderer().render("empty", "void", body);
        assertCompile(input, output);
    }

    private void assertCompile(String input, String expected) {
        var compiler = new Compiler(input);
        var output = compiler.compile();
        assertEquals(expected, output);
    }

    @Test
    void type() {
        var value = new ReturnRenderer().render();
        var body = new BlockRenderer().render(value);
        var input = new MagmaFunctionRenderer().render("supplier", "U16", body);
        var output = new CFunctionRenderer().render("supplier", "unsigned int", body);
        assertCompile(input, output);
    }

    @Test
    void body() {
        var value = new ReturnRenderer().render();
        var body = new BlockRenderer().render(value);
        var input = new MagmaFunctionRenderer().render("supplier", "I16", body);
        var output = new CFunctionRenderer().render("supplier", "int", body);
        assertCompile(input, output);
    }
}