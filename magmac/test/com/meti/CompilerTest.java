package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class CompilerTest {
    private static void assertCompile(String input, String expected) {
        try {
            var actual = Compiler.compile(input);
            assertEquals(expected, actual);
        } catch (CompileException e) {
            fail(e);
        }
    }

    @Test
    void importName() {
        assertCompile("import org.junit.jupiter.api.Test;", "import { Test } from org.junit.jupiter.api;");
    }

    @Test
    void importParent() {
        assertCompile("import parent.Child;", "import { Child } from parent;");
    }
}