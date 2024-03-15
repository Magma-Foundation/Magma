package com.meti.compile;

import org.junit.jupiter.api.Test;

class CompilerTest extends CompiledTest {

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