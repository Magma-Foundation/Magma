package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.CompiledTest.assertCompile;
import static com.meti.FeatureTest.TEST_SYMBOL;
import static com.meti.JavaLang.*;

public class RecordFeatureTest {
    public static final String TEST_BODY = "0";

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testSimpleRecords(String name) {
        assertCompile(renderRecord(name), new MagmaClassNodeBuilder().setPrefix("").setName(name).setContent("").createMagmaClassNode().render());
    }

    @Test
    void testPublicKeyword() {
        assertCompile(renderRecord(Lang.PUBLIC_KEYWORD, TEST_SYMBOL), new MagmaClassNodeBuilder().setPrefix(Lang.EXPORT_KEYWORD).setName(TEST_SYMBOL).setContent("").createMagmaClassNode().render());
    }

    @Test
    void testBody() {
        assertCompile(renderRecord("", TEST_SYMBOL, "{}"),
                new MagmaClassNodeBuilder().setPrefix("").setName(TEST_SYMBOL).setContent("").createMagmaClassNode().render());
    }
}
