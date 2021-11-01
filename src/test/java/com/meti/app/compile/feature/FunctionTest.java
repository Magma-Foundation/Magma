package com.meti.app.compile.feature;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.feature.FeatureTest.assertCompile;

public class FunctionTest {
    @Test
    void test() {
        assertCompile("def empty() : Void => {}", "void empty(){}");
    }
}
