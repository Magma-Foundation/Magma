package com.meti;

import org.junit.jupiter.api.Test;

import static com.meti.FeatureTest.assertWithinClass;

public class MethodFeatureTest {
    @Test
    void test() {
        assertWithinClass("void empty(){}",
                Compiler.renderMagmaMethodWithType("", "empty", "Void", "{}"));

    }
}
