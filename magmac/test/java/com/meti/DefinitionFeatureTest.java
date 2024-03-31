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
    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void testSimpleDefinition(String name) {
        assertCompile(renderJavaClass(TEST_SYMBOL, renderDefinition(name, "int")),
                renderMagmaClass(TEST_SYMBOL, renderMagmaDefinition(name, "I32")));
    }

    @Test
    void testType() {
        assertCompile(renderJavaClass(TEST_SYMBOL, renderDefinition(TEST_SYMBOL, "long")),
                renderMagmaClass(TEST_SYMBOL, renderMagmaDefinition(TEST_SYMBOL, "I64")));
    }
}
