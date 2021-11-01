package com.meti.app.compile.feature;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.feature.FeatureTest.assertCompile;

class ReturnTest {
    @Test
    void another() {
        assertInteger(69);
    }

    private static void assertInteger(int value) {
        var actual = "return " + value;
        var expected = actual + ";";
        assertCompile(actual, expected);
    }

    @Test
    void returns() {
        assertInteger(420);
    }
}