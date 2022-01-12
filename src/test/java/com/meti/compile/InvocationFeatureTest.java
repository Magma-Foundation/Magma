package com.meti.compile;

import org.junit.jupiter.api.Test;

public class InvocationFeatureTest {
    @Test
    void one_parameter() {
        CompiledTest.assertSourceCompile("test(1)", "test(1)");
    }

    @Test
    void test() {
        CompiledTest.assertSourceCompile("test()", "test()");
    }

    @Test
    void two_parameters() {
        CompiledTest.assertSourceCompile("test(1, 2)", "test(1,2)");
    }
}
