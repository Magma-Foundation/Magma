package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.FeatureTest.*;
import static com.meti.Lang.renderJavaImport;
import static com.meti.Lang.renderMagmaImport;

public class ImportTest {
    @Test
    void importStatic() {
        assertRunBeforeClass(
                renderJavaImport(TEST_LOWER_SYMBOL, Lang.TEST_UPPER_SYMBOL, Lang.STATIC_KEYWORD_WITH_SPACE),
                renderMagmaImport(TEST_LOWER_SYMBOL, Lang.TEST_UPPER_SYMBOL));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void importMultiple(int count) {
        var inputImports = repeatAndJoin(count, index -> renderJavaImport(TEST_LOWER_SYMBOL, Lang.TEST_UPPER_SYMBOL + index));
        var outputImports = repeatAndJoin(count, index -> renderMagmaImport(TEST_LOWER_SYMBOL, Lang.TEST_UPPER_SYMBOL + index));

        assertRunBeforeClass(inputImports, outputImports);
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void importParent(String name) {
        assertRunBeforeClass(renderJavaImport(name, Lang.TEST_UPPER_SYMBOL), renderMagmaImport(name, Lang.TEST_UPPER_SYMBOL));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void importChild(String name) {
        assertRunBeforeClass(renderJavaImport(TEST_LOWER_SYMBOL, name), renderMagmaImport(TEST_LOWER_SYMBOL, name));
    }
}
