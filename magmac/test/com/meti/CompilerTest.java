package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CompilerTest {

    @Test
    void compileAnotherPackage() {
        assertTrue(new Compiler("package foo;").compile().isEmpty());
    }
}