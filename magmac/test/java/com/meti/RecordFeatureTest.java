package com.meti;

import com.meti.magma.MagmaClassNodeBuilder;
import com.meti.node.MapNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.CompiledTest.assertCompile;
import static com.meti.FeatureTest.TEST_SYMBOL;

public class RecordFeatureTest {
    public static final MapNode.Builder DEFAULT_INPUT;
    public static final MagmaClassNodeBuilder DEFAULT_OUTPUT = new MagmaClassNodeBuilder().withName(TEST_SYMBOL);

    static {
        MapNode.Builder recordNodeBuilder = MapNode.Builder("record").string("name", TEST_SYMBOL);
        DEFAULT_INPUT = recordNodeBuilder.string("body", "{}");
    }

    @Test
    void oneParameter() {
        assertCompile(DEFAULT_INPUT, DEFAULT_OUTPUT);
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testSimpleRecords(String name) {
        assertCompile(DEFAULT_INPUT.string("name", name), DEFAULT_OUTPUT.withName(name));
    }

    @Test
    void testPublicKeyword() {
        assertCompile(DEFAULT_INPUT.string("prefix", Lang.PUBLIC_KEYWORD), DEFAULT_OUTPUT.withPrefix(Lang.EXPORT_KEYWORD_WITH_SPACE));
    }

    @Test
    void testBody() {
        assertCompile(DEFAULT_INPUT, DEFAULT_OUTPUT);
    }

    @Test
    void testParameter() {
        assertCompile(DEFAULT_INPUT, DEFAULT_OUTPUT);
    }
}
