package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilerTest {
    @Test
    void importName() {
        var output = new Compiler("import parent.Child1;").compile();
        assertEquals("import { Child1 } from parent;", output);
    }
}