package com.meti.compile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilerTest {
    private static void assertCompile(String input, String output) throws CompileException {
        var actual = new Compiler(input).compile();
        assertEquals(output, actual);
    }

    @Test
    void test1() throws CompileException {
        assertCompile("SOURCE", "SOURCE");
    }

    @Test
    void field() throws CompileException {
        assertCompile("first.second", "first.second");
    }

    @Test
    void test2() throws CompileException {
        assertCompile("new Application(SOURCE)", "Application(SOURCE)");
    }

    @Test
    void test3() throws CompileException {
        assertCompile("new Application(SOURCE).run()", "Application(SOURCE).run()");
    }

    @Test
    void test4() throws CompileException {
        assertCompile("new Application(SOURCE).run", "Application(SOURCE).run");
    }


    @Test
    void test() throws CompileException {
        assertCompile("runWithSource()", "runWithSource()");
    }
}