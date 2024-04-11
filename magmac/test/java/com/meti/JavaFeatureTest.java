package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.Application.*;
import static com.meti.CompiledTest.*;
import static org.junit.jupiter.api.Assertions.*;

public class JavaFeatureTest {
    private static void assertJava(String input) {
        try {
            assertEquals(input, compileMagmaToJava(compileJavaToMagma(input)));
        } catch (CompileException e) {
            fail(e);
        }
    }

    private static void assertJavaClassContent(String content) {
        assertJava(renderJavaClass(TEST_UPPER_SYMBOL, renderBlock(content)));
    }

    @Test
    void javaInvalid() {
        assertThrows(CompileException.class, () -> compileJavaToMagma(""));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void javaSimple(String name) {
        assertJava(renderJavaClass(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void definitionName(String name) {
        assertJavaClassContent(renderJavaDefinition(name, TEST_DEFINITION_TYPE, TEST_STRING));
    }

    @Test
    void definitionType() {
        assertJavaClassContent(renderJavaDefinition(TEST_LOWER_SYMBOL, INT_TYPE, TEST_STRING));
    }

    @Test
    void multipleDefinitions() {
        assertJavaClassContent(renderJavaDefinition(TEST_LOWER_SYMBOL, INT_TYPE, TEST_STRING) + renderJavaDefinition(TEST_LOWER_SYMBOL, INT_TYPE, TEST_STRING));
    }

    @Test
    void definitionValue() {
        assertJavaClassContent(renderJavaDefinition(TEST_LOWER_SYMBOL, INT_TYPE, TEST_NUMERIC));
    }

    @Test
    void javaPublic() {
        assertJava(PUBLIC_KEYWORD + renderJavaClass(TEST_UPPER_SYMBOL));
    }
}
