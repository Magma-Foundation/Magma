package com.meti.app.compile.feature;

import com.meti.app.clang.CRenderer;
import com.meti.app.compile.CompileException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.meti.app.compile.feature.FeatureTest.assertCompile;

class ReturnTest {
    @Test
    void another() {
        assertInteger(69);
    }

    private static void assertInteger(int value) {
        try {
            var rendered = new CRenderer(new Return(new IntegerNode(value))).process()
                    .asString()
                    .orElse("");
            assertCompile(rendered, rendered);
        } catch (CompileException e) {
            Assertions.fail(e);
        }
    }

    @Test
    void returns() {
        assertInteger(420);
    }
}