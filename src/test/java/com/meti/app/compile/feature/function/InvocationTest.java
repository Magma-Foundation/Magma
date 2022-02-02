package com.meti.app.compile.feature.function;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.CompiledTest.assertSourceCompile;

public class InvocationTest {
    @Test
    void block() {
        assertSourceCompile("{test(1);}", "{test(1);}");
    }

    @Test
    void nominal() {
        assertSourceCompile("def test() = {}test()", "void test(){}test()");
    }

    @Test
    void one_parameter() {
        assertSourceCompile("test(1)", "test(1)");
    }

    @Test
    void two_parameters() {
        assertSourceCompile("test(1, 2)", "test(1,2)");
    }

    @Test
    void valuesInStrings() {
        assertSourceCompile("test(\"val:ue\")", "test(\"val:ue\")");
    }
}
