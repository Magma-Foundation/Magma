package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MCCompilerTest {
    @Test
    void name() {
        assertCompile("int test(){return 0;}", "def test() : I16 => {return 0;}");
    }

    private void assertCompile(String source, String target) {
        var compiler = new MCCompiler(target);
        var output = compiler.compile();
        assertEquals(source, output);
    }

    @Test
    void type() {
        assertCompile("unsigned int test(){return 0;}", "def test() : U16 => {return 0;}");
    }
}