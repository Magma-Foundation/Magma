package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CompilerTest {
    @Test
    void empty() {
        assertCompile("", "");
    }

    private void assertFunction(String name, String type) {
        try {
            var input = new MagmaRenderer(name, type).render();
            var output = new CRenderingStage(name, type, 0).render();
            assertCompile(input, output);
        } catch (CompileException e) {
            fail(e);
        }
    }

    @Test
    void name() {
        assertFunction("empty", "I16");
    }

    private static void assertCompile(String input, String output) {
        try {
            var actual = new Compiler(input).compile();
            assertEquals(output, actual);
        } catch (CompileException e) {
            fail(e);
        }
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
