package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.assertSourceCompile;

public class LambdaTest {
    @Test
    void invoked() {
        assertSourceCompile("test(() => {})", "void __lambda0__(){}test(__lambda0__)");
    }
}
