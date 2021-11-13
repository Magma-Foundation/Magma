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
        assertFunction("empty");
    }

    private void assertFunction(String empty) {
        var input = new MagmaRenderer(empty).render();
        var output = new CRenderer(empty).render();
        assertCompile(input, output);
    }

    @Test
    void testMain() {
        assertFunction("main");
    }
}
