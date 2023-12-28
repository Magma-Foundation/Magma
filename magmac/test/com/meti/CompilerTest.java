package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CompilerTest {
    private static void assertCompile(String expected, String input) {
        assertEquals(expected, new Compiler(input).compile());
    }

    @Test
    void importParent() {
        assertCompile("import { Child } from bar;", "import bar.Child");
    }

    @Test
    void importChild() {
        assertCompile("import { Bar } from foo;", "import foo.Bar");
    }

    @Test
    void compileAnotherPackage() {
        assertTrue(new Compiler("package foo;").compile().isEmpty());
    }
}