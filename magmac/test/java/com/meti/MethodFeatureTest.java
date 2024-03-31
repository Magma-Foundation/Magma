package com.meti;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.FeatureTest.assertWithinClass;

public class MethodFeatureTest {
    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void testSimpleMethod(String name) {
        assertWithinClass("void " + name + "(){}",
                Compiler.renderMagmaMethodWithType("", name, "Void", "{}"));
    }
}
