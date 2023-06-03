package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilerTest {
    @Test
    void simple() {
        var compiler = new Compiler("import java.io.IOException;");
        var actual = compiler.compile();
        assertEquals("import { IOException } from java.io;", actual);
    }
}