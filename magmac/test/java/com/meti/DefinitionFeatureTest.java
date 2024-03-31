package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.CompiledTest.assertCompile;
import static com.meti.Compiler.renderMagmaClass;
import static com.meti.Compiler.renderMagmaDefinition;
import static com.meti.FeatureTest.TEST_SYMBOL;
import static com.meti.JavaLang.renderDefinition;
import static com.meti.JavaLang.renderJavaClass;

public class DefinitionFeatureTest {
    private static void assertWithinClass(String input, String output) {
        assertCompile(renderJavaClass(TEST_SYMBOL, input), renderMagmaClass(TEST_SYMBOL, output));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void testSimpleDefinition(String name) {
        assertWithinClass(renderDefinition(name, "int"), renderMagmaDefinition(name, "I32"));
    }

    @Test
    void testType() {
        assertWithinClass(renderDefinition(TEST_SYMBOL, "long"), renderMagmaDefinition(TEST_SYMBOL, "I64"));
    }
}
