package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilerTest {
    private static void assertCompile(String input, String output) {
        var actual = new Compiler(input).compile();
        assertEquals(output, actual);
    }

    @Test
    void importParent() {
        assertCompile("import foo.Bar;", "import { Bar } from foo;");
    }

    @Test
    void importName() {
        assertCompile("import parent.Child1;", "import { Child1 } from parent;");
    }
}