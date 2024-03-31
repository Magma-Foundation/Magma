package com.meti;

import com.meti.magma.MagmaClassNodeBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.CompiledTest.assertCompile;
import static com.meti.FeatureTest.TEST_SYMBOL;
import static com.meti.java.JavaLang.*;

public class RecordFeatureTest {
    public static final String TEST_BODY = "0";

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testSimpleRecords(String name) {
        assertCompile(renderRecord(name), new MagmaClassNodeBuilder().withPrefix("").withName(name).withContent("").build().render());
    }

    @Test
    void testPublicKeyword() {
        assertCompile(renderRecord(Lang.PUBLIC_KEYWORD, TEST_SYMBOL), new MagmaClassNodeBuilder().withPrefix(Lang.EXPORT_KEYWORD).withName(TEST_SYMBOL).withContent("").build().render());
    }

    @Test
    void testBody() {
        assertCompile(renderRecord("", TEST_SYMBOL, "{}"),
                new MagmaClassNodeBuilder().withPrefix("").withName(TEST_SYMBOL).withContent("").build().render());
    }
}
