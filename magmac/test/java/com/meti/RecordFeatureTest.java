package com.meti;

import com.meti.java.RecordNodeBuilder;
import com.meti.magma.MagmaClassNodeBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.CompiledTest.assertCompile;
import static com.meti.FeatureTest.TEST_SYMBOL;

public class RecordFeatureTest {

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testSimpleRecords(String name) {
        assertCompile(new RecordNodeBuilder()
                .setPrefix("")
                .setName(name)
                .setBody("{}").createRecordNode().render(), new MagmaClassNodeBuilder().withPrefix("").withName(name).withContent("").build().render());
    }

    @Test
    void testPublicKeyword() {
        assertCompile(new RecordNodeBuilder()
                .setPrefix(Lang.PUBLIC_KEYWORD)
                .setName(TEST_SYMBOL)
                .setBody("{}").createRecordNode().render(), new MagmaClassNodeBuilder().withPrefix(Lang.EXPORT_KEYWORD).withName(TEST_SYMBOL).withContent("").build().render());
    }

    @Test
    void testBody() {
        assertCompile(new RecordNodeBuilder().setPrefix("").setName(TEST_SYMBOL).setBody("{}").createRecordNode().render(),
                new MagmaClassNodeBuilder().withPrefix("").withName(TEST_SYMBOL).withContent("").build().render());
    }
}
