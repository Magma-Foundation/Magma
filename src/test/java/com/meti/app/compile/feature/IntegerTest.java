package com.meti.app.compile.feature;

import com.meti.app.clang.CRenderingStage;
import org.junit.jupiter.api.Test;

import static com.meti.app.compile.feature.FeatureTest.assertCompile;

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
        var rendered = new CRenderingStage(new IntegerNode(value)).process()
                .asString()
                .orElse("");
        assertCompile(rendered, rendered);
    }
}