package com.meti;

import org.junit.jupiter.api.Test;

import static com.meti.CompiledTest.assertCompile;

class MCCompilerTest {
    @Test
    void empty() {
        assertCompile("def test() : Void => {}", "void test(){}");
    }

    @Test
    void name() {
        assertCompile("def test() : I16 => {return 0;}", "int test(){return 0;}");
    }

    @Test
    void type() {
        assertCompile("def test() : U16 => {return 0;}", "unsigned int test(){return 0;}");
    }
}