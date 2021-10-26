package com.meti.feature;

import com.meti.Compiler;
import com.meti.Return;
import org.junit.jupiter.api.Test;

import static com.meti.feature.FeatureTest.assertCompile;

class ReturnTest {
    @Test
    void another() {
        assertInteger("69");
    }

    private static void assertInteger(final String value) {
        var rendered = Compiler.renderC(new Return(new IntegerNode(Integer.parseInt(value))));
        assertCompile(rendered, rendered);
    }

    @Test
    void returns() {
        assertInteger("420");
    }
}