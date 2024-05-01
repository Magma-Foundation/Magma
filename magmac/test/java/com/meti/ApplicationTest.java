package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.FeatureTest.TEST_UPPER_SYMBOL;
import static com.meti.FeatureTest.assertRun;
import static com.meti.Lang.*;

public class ApplicationTest {
    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void definitionName(String name) {
        assertRun(renderJavaClass(TEST_UPPER_SYMBOL, "", renderJavaDefinition(name)),
                renderMagmaFunction(TEST_UPPER_SYMBOL, "", renderMagmaDefinition(name)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void className(String name) {
        assertRun(renderJavaClass(name), renderMagmaFunction(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void packageStatement(String name) {
        assertRun(PACKAGE_KEYWORD_WITH_SPACE + name + STATEMENT_END + renderJavaClass(TEST_UPPER_SYMBOL), renderMagmaFunction(TEST_UPPER_SYMBOL));
    }

    @Test
    void classPublic() {
        assertRun(renderJavaClass(TEST_UPPER_SYMBOL, PUBLIC_KEYWORD_WITH_SPACE), renderMagmaFunction(TEST_UPPER_SYMBOL, EXPORT_KEYWORD_WITH_SPACE));
    }
}