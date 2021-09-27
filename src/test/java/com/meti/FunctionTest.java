package com.meti;

import org.junit.jupiter.api.Test;

class FunctionTest extends CompilerTest {
    @Test
    void body() {
        assertCompile("def test() : I16 => {return 420;}", "int test(){return 420;}");
    }

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

    @Test
    void parameters() {
        assertCompile("def empty(first : I16, second : I16) : Void => {}", "void empty(int first,int second){}");
    }

    @Test
    void type() {
        assertCompile("def test() : I16 => {return 0;}", "int test(){return 0;}");
    }
}