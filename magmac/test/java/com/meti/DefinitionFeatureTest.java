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
                .render(), new MagmaDefinitionBuilder().setFlagString("").setMutabilityString(LET_KEYWORD).setName(name).setType(I32).setValue("0").createMagmaDefinition().render());
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void testMultipleDefinitions(int count) {
        FeatureTest.assertWithinClass(DEFAULT_BUILDER.build().render().repeat(count),
                new MagmaDefinitionBuilder().setFlagString("").setMutabilityString(LET_KEYWORD).setName(TEST_SYMBOL).setType(I32).setValue("0").createMagmaDefinition().render().repeat(count));
    }

    @Test
    void testType() {
        FeatureTest.assertWithinClass(DEFAULT_BUILDER
                .setType(LONG)
                .build()
                .render(), new MagmaDefinitionBuilder().setFlagString("").setMutabilityString(LET_KEYWORD).setName(TEST_SYMBOL).setType(I64).setValue("0").createMagmaDefinition().render());
    }

    @Test
    void testValue() {
        FeatureTest.assertWithinClass(DEFAULT_BUILDER
                .withValue(TEST_VALUE)
                .build()
                .render(), new MagmaDefinitionBuilder().setFlagString("").setMutabilityString(LET_KEYWORD).setName(TEST_SYMBOL).setType(I32).setValue(TEST_VALUE).createMagmaDefinition().render());
    }

    @Test
    void testFinal() {
        FeatureTest.assertWithinClass(DEFAULT_BUILDER
                .withFlagString(FINAL_KEYWORD + " ")
                .withValue(TEST_VALUE)
                .build()
                .render(), new MagmaDefinitionBuilder().setFlagString("").setMutabilityString(CONST_KEYWORD).setName(TEST_SYMBOL).setType(I32).setValue(TEST_VALUE).createMagmaDefinition().render());
    }

    @ParameterizedTest
    @ValueSource(strings = {TEST_SYMBOL, TEST_SYMBOL + "_more"})
    void testStatic(String className) {
        assertCompile(renderJavaClass(className, DEFAULT_BUILDER
                        .withValue(TEST_VALUE)
                        .withFlagString("static ")
                        .build()
                        .render()),
                renderObject(className, new MagmaDefinitionBuilder().setFlagString("").setMutabilityString(LET_KEYWORD).setName(TEST_SYMBOL).setType(I32).setValue(TEST_VALUE).createMagmaDefinition().render())
                + "\n\n"
                + renderMagmaClass(className, ""));
    }

    @Test
    void testPublic() {
        FeatureTest.assertWithinClass(DEFAULT_BUILDER.withFlagString("public ")
                        .withValue(TEST_VALUE)
                        .build()
                        .render(),
                new MagmaDefinitionBuilder().setFlagString("pub ").setMutabilityString(LET_KEYWORD).setName(TEST_SYMBOL).setType(I32).setValue(TEST_VALUE).createMagmaDefinition().render());
    }
}
