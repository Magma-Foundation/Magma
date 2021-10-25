package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilerTest {
    @Test
    void integer() {
        var compile = new Compiler(Compiler.renderInteger());
        var output = compile.compile();
        assertEquals(Compiler.renderInteger(), output);
    }

}