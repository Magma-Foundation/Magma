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

    public static String renderJavaDefinition(String name, String type) {
        return Application.renderJavaDefinition(name, type, TEST_STRING);
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
        assertJavaClassMember(renderJavaDefinition(name, CompiledTest.TEST_DEFINITION_TYPE));
    }

    private static void assertJavaClassMember(String content) {
        assertJava(renderJavaClass(TEST_UPPER_SYMBOL, renderBlock(content)));
    }

    @Test
    void definitionType() {
        assertJavaClassMember(renderJavaDefinition(TEST_LOWER_SYMBOL, INT_TYPE));
    }

    @Test
    void definitionValue() {
        assertJavaClassMember(Application.renderJavaDefinition(TEST_LOWER_SYMBOL, INT_TYPE, TEST_NUMERIC));
    }

    @Test
    void javaPublic() {
        assertJava(PUBLIC_KEYWORD + renderJavaClass(TEST_UPPER_SYMBOL));
    }
}
