package com.meti.compile.feature;

import com.meti.clang.CRenderingStage;
import org.junit.jupiter.api.Test;

import static com.meti.compile.feature.FeatureTest.assertCompile;

class ReturnTest {
    @Test
    void another() {
        assertInteger(69);
    }

    private static void assertInteger(int value) {
        var rendered = new CRenderingStage(new Return(new IntegerNode(value))).process()
                .asString()
                .orElse("");
        assertCompile(rendered, rendered);
    }

    @Test
    void returns() {
        assertInteger(420);
    }
}