package com.meti;

import com.meti.magma.DeclarationNodeBuilder;
import com.meti.magma.MagmaClassNodeBuilder;
import com.meti.magma.MagmaDefinitionBuilder;
import com.meti.magma.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.CompiledTest.assertCompile;
import static com.meti.FeatureTest.TEST_SYMBOL;
import static com.meti.java.JavaLang.renderJavaClass;

public class DefinitionFeatureTest {

    public static final String TEST_VALUE = "100";
    public static final DeclarationNodeBuilder DEFAULT_BUILDER = new DeclarationNodeBuilder()
            .withValue("0")
            .withName(TEST_SYMBOL)
            .setType(Lang.INT);

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void testSimpleDefinition(String name) {
        FeatureTest.assertWithinClass(DEFAULT_BUILDER
                .withName(name)
                .build()
                .render(), new MagmaDefinitionBuilder().withFlags("").withMutability(Lang.LET_KEYWORD).withName(name).withType(Lang.I32).withValue("0").build().render());
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void testMultipleDefinitions(int count) {
        FeatureTest.assertWithinClass(DEFAULT_BUILDER.build().render().repeat(count),
                new MagmaDefinitionBuilder().withFlags("").withMutability(Lang.LET_KEYWORD).withName(TEST_SYMBOL).withType(Lang.I32).withValue("0").build().render().repeat(count));
    }

    @Test
    void testType() {
        FeatureTest.assertWithinClass(DEFAULT_BUILDER
                .setType(Lang.LONG)
                .build()
                .render(), new MagmaDefinitionBuilder().withFlags("").withMutability(Lang.LET_KEYWORD).withName(TEST_SYMBOL).withType(Lang.I64).withValue("0").build().render());
    }

    @Test
    void testValue() {
        FeatureTest.assertWithinClass(DEFAULT_BUILDER
                .withValue(TEST_VALUE)
                .build()
                .render(), new MagmaDefinitionBuilder().withFlags("").withMutability(Lang.LET_KEYWORD).withName(TEST_SYMBOL).withType(Lang.I32).withValue(TEST_VALUE).build().render());
    }

    @Test
    void testFinal() {
        FeatureTest.assertWithinClass(DEFAULT_BUILDER
                .withFlagString(Lang.FINAL_KEYWORD + " ")
                .withValue(TEST_VALUE)
                .build()
                .render(), new MagmaDefinitionBuilder().withFlags("").withMutability(Lang.CONST_KEYWORD).withName(TEST_SYMBOL).withType(Lang.I32).withValue(TEST_VALUE).build().render());
    }

    @ParameterizedTest
    @ValueSource(strings = {TEST_SYMBOL, TEST_SYMBOL + "_more"})
    void testStatic(String className) {
        String content = new MagmaDefinitionBuilder().withFlags("").withMutability(Lang.LET_KEYWORD).withName(TEST_SYMBOL).withType(Lang.I32).withValue(TEST_VALUE).build().render();
        assertCompile(renderJavaClass(className, DEFAULT_BUILDER
                        .withValue(TEST_VALUE)
                        .withFlagString("static ")
                        .build()
                        .render()),
                new ObjectNode("", className, content).render()
                + "\n\n"
                + new MagmaClassNodeBuilder().withPrefix("").withName(className).withContent("").build().render());
    }

    @Test
    void testPublic() {
        FeatureTest.assertWithinClass(DEFAULT_BUILDER.withFlagString("public ")
                        .withValue(TEST_VALUE)
                        .build()
                        .render(),
                new MagmaDefinitionBuilder().withFlags("pub ").withMutability(Lang.LET_KEYWORD).withName(TEST_SYMBOL).withType(Lang.I32).withValue(TEST_VALUE).build().render());
    }
}
