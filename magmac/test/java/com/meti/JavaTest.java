package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.FeatureTest.TEST_UPPER_SYMBOL;
import static com.meti.JavaFeatureTest.assertJava;
import static com.meti.JavaLang.*;
import static com.meti.Lang.renderBlock;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JavaTest {
    private static void assertWithinClass(String definition) {
        assertJava(TEST_UPPER_SYMBOL, renderJavaClass(TEST_UPPER_SYMBOL, renderBlock(definition)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void javaSmallest(String className) {
        assertJava(className, renderJavaClass(className));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void javaField(String name) {
        assertWithinClass(renderJavaDefinition(name));
    }

    @Test
    void javaFieldType() {
        assertWithinClass(renderJavaDefinition("test", LONG_TYPE));
    }

    @Test
    void javaFieldValue() {
        assertWithinClass(renderJavaDefinition("test", LONG_TYPE, "100"));
    }

    @Test
    void invalidJava() {
        assertThrows(CompileException.class, () -> JavaToMagmaCompiler.compileJavaToMagma(""));
    }
}
