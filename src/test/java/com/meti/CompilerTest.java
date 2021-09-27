package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilerTest {
    @Test
    void name() {
        assertCompile("def empty() : Void => {}", "void empty(){}");
    }

    @Test
    void param() {
        assertCompile("def empty(value : I16) : Void => {}", "void empty(int value){}");
    }

    @Test
    void param_name() {
        assertCompile("def empty(test : I16) : Void => {}", "void empty(int test){}");
    }

    @Test
    void param_type() {
        assertCompile("def empty(value : U16) : Void => {}", "void empty(unsigned int value){}");
    }

    private void assertCompile(String input, String output) {
        var compiler = new Compiler(input);
        var actual = compiler.compile();
        assertEquals(output, actual);
    }

    @Test
    void type() {
        assertCompile("def test() : I16 => {return 0;}", "int test(){return 0;}");
    }
}