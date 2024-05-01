package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.FeatureTest.*;
import static com.meti.Lang.*;

public class ApplicationTest {
    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void definitionName(String name) {
        assertRun(JavaLang.renderJavaClass(TEST_UPPER_SYMBOL, "", JavaLang.renderJavaDefinition(JavaLang.INT_KEYWORD, name)),
                MagmaLang.renderMagmaFunction(TEST_UPPER_SYMBOL, "", MagmaLang.renderMagmaDefinition(name, MagmaLang.I32_KEYWORD)));
    }

    @Test
    void definitionType() {
        assertRun(JavaLang.renderJavaClass(TEST_UPPER_SYMBOL, "", JavaLang.renderJavaDefinition(JavaLang.LONG_KEYWORD, TEST_LOWER_SYMBOL)),
                MagmaLang.renderMagmaFunction(TEST_UPPER_SYMBOL, "", MagmaLang.renderMagmaDefinition(TEST_LOWER_SYMBOL, MagmaLang.I64_KEYWORD)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void className(String name) {
        assertRun(JavaLang.renderJavaClass(name), MagmaLang.renderMagmaFunction(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void packageStatement(String name) {
        assertRun(JavaLang.PACKAGE_KEYWORD_WITH_SPACE + name + STATEMENT_END + JavaLang.renderJavaClass(TEST_UPPER_SYMBOL), MagmaLang.renderMagmaFunction(TEST_UPPER_SYMBOL));
    }

    @Test
    void classPublic() {
        assertRun(JavaLang.renderJavaClass(TEST_UPPER_SYMBOL, PUBLIC_KEYWORD_WITH_SPACE), MagmaLang.renderMagmaFunction(TEST_UPPER_SYMBOL, MagmaLang.EXPORT_KEYWORD_WITH_SPACE));
    }
}