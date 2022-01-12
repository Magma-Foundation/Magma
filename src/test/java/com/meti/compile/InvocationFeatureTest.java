package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.assertSourceCompile;

public class InvocationFeatureTest {
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
