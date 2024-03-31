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
        assertCompile(JavaLang.renderJavaClass(name, ""), new MagmaClassNodeBuilder().setPrefix("").setName(name).setContent("").createMagmaClassNode().render());
    }

    @Test
    void testPublicKeyword() {
        assertCompile(new JavaClassNodeBuilder().setPrefix(Compiler.PUBLIC_KEYWORD).setName(FeatureTest.TEST_SYMBOL).setContent("").createJavaClassNode().renderJavaClass(), new MagmaClassNodeBuilder().setPrefix("export ").setName(FeatureTest.TEST_SYMBOL).setContent("").createMagmaClassNode().render());
    }

    @Test
    void testBody() {
        assertCompile(JavaLang.renderJavaClass(FeatureTest.TEST_SYMBOL, TEST_BODY),
                new MagmaClassNodeBuilder().setPrefix("").setName(FeatureTest.TEST_SYMBOL).setContent(TEST_BODY).createMagmaClassNode().render());
    }
}
