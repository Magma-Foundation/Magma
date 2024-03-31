package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.CompiledTest.assertCompile;
import static com.meti.Compiler.*;
import static com.meti.FeatureTest.TEST_SYMBOL;

public class RecordFeatureTest {
    public static final String TEST_BODY = "0";

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testSimpleRecords(String name) {
        assertCompile(JavaLang.renderRecord(name), renderMagmaClass(name, ""));
    }

    @Test
    void testPublicKeyword() {
        assertCompile(renderJavaClass(PUBLIC_KEYWORD, TEST_SYMBOL, ""), renderMagmaClass("export ", TEST_SYMBOL, ""));
    }

    @Test
    void testBody() {
        assertCompile(JavaLang.renderJavaClass(TEST_SYMBOL, TEST_BODY),
                renderMagmaClass(TEST_SYMBOL, TEST_BODY));
    }
}
