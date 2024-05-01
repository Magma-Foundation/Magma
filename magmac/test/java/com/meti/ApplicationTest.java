package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.FeatureTest.*;
import static com.meti.JavaLang.*;
import static com.meti.Lang.PUBLIC_KEYWORD_WITH_SPACE;
import static com.meti.Lang.STATEMENT_END;
import static com.meti.MagmaLang.*;

public class ApplicationTest {
    private static void assertRunWithinClass(String input, String output) {
        assertRun(renderJavaClass(TEST_UPPER_SYMBOL, "", input), renderMagmaFunction(TEST_UPPER_SYMBOL, "", output));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void definitionName(String name) {
        assertRunWithinClass(renderJavaDefinition(name, INT_KEYWORD, "0"), renderMagmaDefinition(name, I32_KEYWORD, "0"));
    }

    @Test
    void definitionType() {
        assertRunWithinClass(renderJavaDefinition(TEST_LOWER_SYMBOL, LONG_KEYWORD, "0"), renderMagmaDefinition(TEST_LOWER_SYMBOL, I64_KEYWORD, "0"));
    }

    @Test
    void definitionValue() {
        var value = "100";
        assertRunWithinClass(
                renderJavaDefinition(TEST_LOWER_SYMBOL, INT_KEYWORD, value),
                renderMagmaDefinition(TEST_LOWER_SYMBOL, I32_KEYWORD, value));
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