package com.meti;

import com.meti.java.RecordNodeBuilder;
import com.meti.magma.MagmaClassNodeBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.FeatureTest.TEST_SYMBOL;

public class RecordFeatureTest {
    public static final RecordNodeBuilder DEFAULT_INPUT = new RecordNodeBuilder().withName(TEST_SYMBOL).withBody("{}");
    public static final MagmaClassNodeBuilder DEFAULT_OUTPUT = new MagmaClassNodeBuilder().withName(TEST_SYMBOL);

    @Test
    void oneParameter() {
        CompiledTest.assertCompile(DEFAULT_INPUT, DEFAULT_OUTPUT);
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testSimpleRecords(String name) {
        CompiledTest.assertCompile(DEFAULT_INPUT.withName(name), DEFAULT_OUTPUT.withName(name));
    }

    @Test
    void testPublicKeyword() {
        CompiledTest.assertCompile(DEFAULT_INPUT.withPrefix(Lang.PUBLIC_KEYWORD), DEFAULT_OUTPUT.withPrefix(Lang.EXPORT_KEYWORD));
    }

    @Test
    void testBody() {
        CompiledTest.assertCompile(DEFAULT_INPUT, DEFAULT_OUTPUT);
    }
}
