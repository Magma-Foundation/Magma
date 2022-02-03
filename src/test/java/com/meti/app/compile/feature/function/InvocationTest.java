package com.meti.app.compile.feature.function;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.CompiledTest.assertSourceCompile;

public class InvocationTest {
    @Test
    void block() {
        assertSourceCompile("def test(value : I16) => {}{test(1);}", "void test(int value){}{test(1);}");
    }

    @Test
    void nominal() {
        assertSourceCompile("def test() => {}test()", "void test(){}test()");
    }

    @Test
    void one_parameter() {
        assertSourceCompile("def test(value : I16) => {}test(1)", "void test(int value){}test(1)");
    }

    @Test
    void two_parameters() {
        assertSourceCompile("def test(first : I16, second : I16) => {}test(1, 2)", "void test(int first,int second){}test(1,2)");
    }

    @Test
    void valuesInStrings() {
        assertSourceCompile("def test(value : &I8) => {}test(\"val:ue\")",
                "void test(char *value){}test(\"val:ue\")");
    }
}
