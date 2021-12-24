package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.assertCompile;

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

    @Test
    void multiple() {
        assertCompile("const x : I16 = 420;const y : U16=69;",
                "int x=420;unsigned int y=69;");
    }
}