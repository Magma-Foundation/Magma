package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompilerTest {
    @Test
    void test() {
        var actual = new Compiler(Compiler.renderJavaImport("IOException")).compile();
        assertEquals(Compiler.renderMagmaImport("IOException"), actual);
    }
}