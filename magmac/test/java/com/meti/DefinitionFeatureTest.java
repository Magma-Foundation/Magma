package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.CompiledTest.assertCompile;
import static com.meti.Compiler.*;
import static com.meti.FeatureTest.TEST_SYMBOL;
import static com.meti.JavaLang.renderDefinition;
import static com.meti.JavaLang.renderJavaClass;

public class DefinitionFeatureTest {

    public static final String TEST_VALUE = "100";

    private static void assertWithinClass(String input, String output) {
        assertCompile(renderJavaClass(TEST_SYMBOL, input), renderMagmaClass(TEST_SYMBOL, output));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void testSimpleDefinition(String name) {
        assertWithinClass(renderDefinition(name, INT), renderMagmaDefinition(name, I32));
    }

    @Test
    void testType() {
        assertWithinClass(renderDefinition(TEST_SYMBOL, LONG), renderMagmaDefinition(TEST_SYMBOL, I64));
    }

    @Test
    void testValue() {
        assertWithinClass(renderDefinition(TEST_SYMBOL, INT, TEST_VALUE), renderMagmaDefinition(TEST_SYMBOL, I32, TEST_VALUE));
    }

    @Test
    void testFinal() {
        assertWithinClass(
                renderDefinition(FINAL_KEYWORD + " ", TEST_SYMBOL, INT, TEST_VALUE),
                renderMagmaDefinition("", CONST_KEYWORD, TEST_SYMBOL, I32, TEST_VALUE));
    }

    @ParameterizedTest
    @ValueSource(strings = {TEST_SYMBOL, TEST_SYMBOL + "_more"})
    void testStatic(String className) {
        assertCompile(
                renderJavaClass(className, renderDefinition("static ", TEST_SYMBOL, INT, TEST_VALUE)),
                renderObject(className, renderMagmaDefinition("", LET_KEYWORD, TEST_SYMBOL, I32, TEST_VALUE)));
    }
}
