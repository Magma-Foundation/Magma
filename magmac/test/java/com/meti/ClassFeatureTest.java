package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.CompiledTest.assertCompile;

public class ClassFeatureTest {
    public static final String TEST_BODY = "0";

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testSimpleClasses(String name) {
        assertCompile(JavaLang.renderJavaClass(name, ""), Compiler.renderMagmaClass(name, ""));
    }

    @Test
    void testPublicKeyword() {
        assertCompile(Compiler.renderJavaClass(Compiler.PUBLIC_KEYWORD, FeatureTest.TEST_SYMBOL, ""), Compiler.renderMagmaClass("export ", FeatureTest.TEST_SYMBOL, ""));
    }

    @Test
    void testBody() {
        assertCompile(JavaLang.renderJavaClass(FeatureTest.TEST_SYMBOL, TEST_BODY),
                Compiler.renderMagmaClass(FeatureTest.TEST_SYMBOL, TEST_BODY));
    }
}
