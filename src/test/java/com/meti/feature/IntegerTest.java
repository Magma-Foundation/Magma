package com.meti.feature;

import com.meti.Compiler;
import org.junit.jupiter.api.Test;

import static com.meti.feature.FeatureTest.assertCompile;

class IntegerTest {
    @Test
    void another() {
        assertInteger(69);
    }

    @Test
    void integer() {
        assertInteger(420);
    }

    private static void assertInteger(int value) {
        var rendered = Compiler.renderC(new IntegerNode(value)).compute();
        assertCompile(rendered, rendered);
    }
}