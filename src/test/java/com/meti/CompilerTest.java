package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CompilerTest {

    @Test
    void compile() {
        assertThrows(CompileException.class, () -> new Compiler("import { Test } from Test").compile());
    }
}