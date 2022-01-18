package com.meti.app.compile;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.CompiledTest.assertSourceCompile;

public class InvocationFeatureTest {
    @Test
    void valuesInStrings(){
        assertSourceCompile("test(\"val:ue\")", "test(\"val:ue\")");
    }

    @Test
    void block() {
        assertSourceCompile("{test(1);}", "{test(1);}");
    }

    @Test
    void one_parameter() {
        assertSourceCompile("test(1)", "test(1)");
    }

    @Test
    void test() {
        assertSourceCompile("test()", "test()");
    }

    @Test
    void two_parameters() {
        assertSourceCompile("test(1, 2)", "test(1,2)");
    }
}
