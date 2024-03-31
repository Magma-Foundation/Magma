package com.meti;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.CompiledTest.assertCompile;

public class DefinitionFeatureTest {
    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void testBodyContent(String name) {
        assertCompile(JavaLang.renderJavaClass(FeatureTest.TEST_NAME, Compiler.renderJavaDefinition(name)),
                Compiler.renderMagmaClass(FeatureTest.TEST_NAME, Compiler.renderMagmaDefinition(name)));
    }
}
