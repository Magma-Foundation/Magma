package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilerTest {
    @Test
    void compile() {
        assertCompile("def test() : I16 => {return 0;}", "int test(){return 0;}");
    }

    private void assertCompile(String input, String output) {
        var compiler = new Compiler(input);
        var actual = compiler.compile();
        assertEquals(output, actual);
    }

    @Test
    void return_type(){
        assertCompile("def test() : U16 => {return 0;}", "unsigned int test(){return 0;}");
    }
}