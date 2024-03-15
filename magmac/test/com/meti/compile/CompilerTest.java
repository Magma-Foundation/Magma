package com.meti.compile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompilerTest {
    @Test
    void test() throws CompileException {
        var actual = new Compiler("runWithSource()").compile();
        assertEquals("runWithSource()", actual);
    }
}