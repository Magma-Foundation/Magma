package com.meti.compile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompilerTest {
    @Test
    void test1() throws CompileException {
        assertCompile("SOURCE", "SOURCE");
    }

    @Test
    void test2() throws CompileException {
        assertCompile("new Application(SOURCE)", "new Application(SOURCE)");
    }


    @Test
    void test() throws CompileException {
        assertCompile("runWithSource()", "runWithSource()");
    }

    private static void assertCompile(String input, String output) throws CompileException {
        var actual = new Compiler(input).compile();
        assertEquals(output, actual);
    }
}