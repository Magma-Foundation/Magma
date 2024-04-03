package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.FeatureTest.assertCompile;
import static com.meti.JavaLang.PUBLIC_KEYWORD;
import static com.meti.JavaLang.renderJavaClass;
import static com.meti.Lang.EMPTY_CONTENT;
import static com.meti.MagmaLang.EXPORT_KEYWORD;
import static com.meti.MagmaLang.renderMagmaFunction;

public class ClassFeatureTest {
    public static final String TEST_SYMBOL = "Test";

    @Test
    void testConstructor() {
        assertCompile(
                renderJavaClass("", TEST_SYMBOL, "{" + TEST_SYMBOL + "(){}" + "}"),
                renderMagmaFunction("", TEST_SYMBOL, EMPTY_CONTENT)
        );
    }

    @Test
    void testPublic() {
        assertCompile(
                renderJavaClass(PUBLIC_KEYWORD, TEST_SYMBOL, "{}"),
                renderMagmaFunction(EXPORT_KEYWORD, TEST_SYMBOL, "{}")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testSimple(String name) {
        assertCompile(renderJavaClass(name), renderMagmaFunction(name));
    }
}
