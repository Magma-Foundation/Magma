package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilerTest {
    @Test
    void importName() throws CompileException {
        var actual = Compiler.compile("import org.junit.jupiter.api.Test;");
        assertEquals("import { Test } from org.junit.jupiter.api;", actual);
    }
}