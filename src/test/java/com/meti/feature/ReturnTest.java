package com.meti.feature;

import com.meti.Compiler;
import com.meti.Return;
import org.junit.jupiter.api.Test;

import static com.meti.feature.FeatureTest.assertCompile;

class ReturnTest {
    @Test
    void another() {
        assertInteger(69);
    }

    private static void assertInteger(int value) {
        var rendered = Compiler.render(new Return(new IntegerNode(value)))
                .asString()
                .orElse("");
        assertCompile(rendered, rendered);
    }

    @Test
    void returns() {
        assertInteger(420);
    }
}