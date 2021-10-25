package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilerTest {
    @Test
    void integer() {
        assertInteger(420);
    }

    @Test
    void another() {
        assertInteger(69);
    }

    private void assertInteger(int value) {
        var compile = new Compiler(Compiler.renderInteger(value));
        var output = compile.compile();
        assertEquals(Compiler.renderInteger(value), output);
    }
}