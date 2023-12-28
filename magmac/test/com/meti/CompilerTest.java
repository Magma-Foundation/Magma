package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CompilerTest {
    @Test
    void importParent() {
        assertEquals("import { Child } from bar;", new Compiler("import bar.Child").compile());
    }

    @Test
    void compileAnotherPackage() {
        assertTrue(new Compiler("package foo;").compile().isEmpty());
    }
}