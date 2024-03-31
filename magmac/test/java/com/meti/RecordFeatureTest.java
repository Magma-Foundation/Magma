package com.meti;

import com.meti.java.RecordNodeBuilder;
import com.meti.java.RenderableBuilder;
import com.meti.magma.MagmaClassNodeBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.FeatureTest.TEST_SYMBOL;

public class RecordFeatureTest {

    private static void assertCompile(RenderableBuilder input, RenderableBuilder output) {
        CompiledTest.assertCompile(input.build().render(), output.build().render());
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testSimpleRecords(String name) {
        assertCompile(new RecordNodeBuilder().withName(name).withBody("{}"),
                new MagmaClassNodeBuilder().withName(name));
    }

    @Test
    void testPublicKeyword() {
        assertCompile(new RecordNodeBuilder().withPrefix(Lang.PUBLIC_KEYWORD).withName(TEST_SYMBOL).withBody("{}"),
                new MagmaClassNodeBuilder()
                        .withPrefix(Lang.EXPORT_KEYWORD)
                        .withName(TEST_SYMBOL));
    }

    @Test
    void testBody() {
        assertCompile(new RecordNodeBuilder().withName(TEST_SYMBOL).withBody("{}"),
                new MagmaClassNodeBuilder().withName(TEST_SYMBOL));
    }
}
