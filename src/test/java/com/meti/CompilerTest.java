package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilerTest {
    @Test
    void name() {
        var compiler = new Compiler("def empty() : Void => {}");
        var output = compiler.compile();
        assertEquals("void empty(){}", output);
    }
}