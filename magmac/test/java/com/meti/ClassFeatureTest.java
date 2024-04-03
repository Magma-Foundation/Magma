package com.meti;

import org.junit.jupiter.api.Test;

import static com.meti.FeatureTest.assertCompile;

public class ClassFeatureTest {

    @Test
    void testSimple() {
        assertCompile(MagmaCompiler.TEST_INPUT, MagmaCompiler.TEST_OUTPUT);
    }
}
