package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.CompiledTest.assertCompile;
import static com.meti.Compiler.*;
import static com.meti.FeatureTest.TEST_SYMBOL;
import static com.meti.JavaLang.renderJavaClass;

public class DefinitionFeatureTest {

    public static final String TEST_VALUE = "100";
    public static final DeclarationNodeBuilder DEFAULT_BUILDER = new DeclarationNodeBuilder()
            .withValue("0")
            .setName(TEST_SYMBOL)
            .setType(INT);

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void testSimpleDefinition(String name) {
        FeatureTest.assertWithinClass(DEFAULT_BUILDER
                .setName(name)
                .build()
                .render(), renderMagmaDefinition(name, I32));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void testMultipleDefinitions(int count) {
        FeatureTest.assertWithinClass(DEFAULT_BUILDER.build().render().repeat(count),
                renderMagmaDefinition(TEST_SYMBOL, I32).repeat(count));
    }

    @Test
    void testType() {
        FeatureTest.assertWithinClass(DEFAULT_BUILDER
                .setType(LONG)
                .build()
                .render(), renderMagmaDefinition(TEST_SYMBOL, I64));
    }

    @Test
    void testValue() {
        FeatureTest.assertWithinClass(DEFAULT_BUILDER
                .withValue(TEST_VALUE)
                .build()
                .render(), renderMagmaDefinition(TEST_SYMBOL, I32, TEST_VALUE));
    }

    @Test
    void testFinal() {
        FeatureTest.assertWithinClass(DEFAULT_BUILDER
                .withFlagString(FINAL_KEYWORD + " ")
                .withValue(TEST_VALUE)
                .build()
                .render(), renderMagmaDefinition("", CONST_KEYWORD, TEST_SYMBOL, I32, TEST_VALUE));
    }

    @ParameterizedTest
    @ValueSource(strings = {TEST_SYMBOL, TEST_SYMBOL + "_more"})
    void testStatic(String className) {
        assertCompile(renderJavaClass(className, DEFAULT_BUILDER
                        .withValue(TEST_VALUE)
                        .withFlagString("static ")
                        .build()
                        .render()),
                renderObject(className, renderMagmaDefinition("", LET_KEYWORD, TEST_SYMBOL, I32, TEST_VALUE))
                + "\n\n"
                + renderMagmaClass(className, ""));
    }

    @Test
    void testPublic() {
        FeatureTest.assertWithinClass(DEFAULT_BUILDER.withFlagString("public ")
                        .withValue(TEST_VALUE)
                        .build()
                        .render(),
                renderMagmaDefinition("pub ", LET_KEYWORD, TEST_SYMBOL, I32, TEST_VALUE));
    }
}
