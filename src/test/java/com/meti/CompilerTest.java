package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompilerTest {
    @Test
    void empty() {
        assertCompile("", "");
    }

    private static void assertCompile(String input, String output) {
        var actual = new Compiler(input).compile();
        assertEquals(output, actual);
    }

    @Test
    void name() {
        assertFunction("empty", "I16");
    }

    private void assertFunction(String name, String type) {
        var input = new MagmaRenderer(name, type).render();
        var output = new CRenderer(name, type).render();
        assertCompile(input, output);
    }

    @Test
    void testMain() {
        assertFunction("main", "I16");
    }

    @Test
    void type() {
        assertFunction("test", "U16");
    }
}
