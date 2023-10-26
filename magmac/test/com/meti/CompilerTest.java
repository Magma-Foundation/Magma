package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilerTest {
    @Test
    void multipleImports() throws CompileException {
        var actual = new Compiler("import parent.Child;import parent.Sibling;").compile();
        assertEquals("import { Child } from parent;import { Sibling } from parent;", actual);
    }
}