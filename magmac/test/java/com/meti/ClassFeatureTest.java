package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.CompiledTest.assertCompile;

public class ClassFeatureTest {
    public static final String TEST_NAME = "Test";
    public static final String TEST_BODY = "0";

    private static String renderJavaClass(String name, String content) {
        return Compiler.renderJavaClass("", name, content);
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testSimpleClasses(String name) {
        assertCompile(renderJavaClass(name, ""), Compiler.renderMagmaClass(name));
    }

    @Test
    void testPublicKeyword() {
        assertCompile(Compiler.renderJavaClass(Compiler.PUBLIC_KEYWORD, TEST_NAME, ""), Compiler.renderExportedMagmaClass(TEST_NAME));
    }

    @Test
    void testBody() {
        assertCompile(renderJavaClass(TEST_NAME, TEST_BODY),
                Compiler.renderMagmaClass(TEST_NAME, TEST_BODY));
    }
}
