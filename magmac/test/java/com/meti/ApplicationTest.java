package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.Compiler.FINAL_KEYWORD_WITH_SPACE;
import static com.meti.FeatureTest.*;
import static com.meti.JavaLang.*;
import static com.meti.Lang.PUBLIC_KEYWORD_WITH_SPACE;
import static com.meti.Lang.STATEMENT_END;
import static com.meti.MagmaLang.*;

public class ApplicationTest {
    public static final JavaDefinitionNodeBuilder BUILDER0 = new JavaDefinitionNodeBuilder()
            .withModifierString(FINAL_KEYWORD_WITH_SPACE)
            .withName(TEST_LOWER_SYMBOL)
            .withType(INT_KEYWORD)
            .withValue("0");

    @Test
    void definitionFinal() {
        assertRunWithinClass(
                renderJavaDefinition(BUILDER0.complete()),
                renderMagmaDefinition(new MagmaDefinitionBuilder().withMutabilityModifier(CONST_KEYWORD_WITH_SPACE).withName(TEST_LOWER_SYMBOL).withType(I32_KEYWORD).withValue("0").build()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void definitionName(String name) {
        assertRunWithinClass(renderJavaDefinition(new JavaDefinitionNodeBuilder()
                        .withName(name)
                        .withType(INT_KEYWORD)
                        .withValue("0")
                        .complete()),
                renderMagmaDefinition(new MagmaDefinitionBuilder().withMutabilityModifier(LET_KEYWORD_WITH_SPACE).withName(name).withType(I32_KEYWORD).withValue("0").build()));
    }

    @Test
    void definitionType() {
        assertRunWithinClass(renderJavaDefinition(new JavaDefinitionNodeBuilder().withName(TEST_LOWER_SYMBOL).withType(LONG_KEYWORD).withValue("0").complete()), renderMagmaDefinition(new MagmaDefinitionBuilder().withMutabilityModifier(LET_KEYWORD_WITH_SPACE).withName(TEST_LOWER_SYMBOL).withType(I64_KEYWORD).withValue("0").build()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void methodName(String name) {
        assertRunWithinClass(renderJavaMethod(name), renderMagmaFunction(name));
    }

    @Test
    void methodParameter() {
        assertRunWithinClass(
                renderJavaMethod(TEST_LOWER_SYMBOL, renderJavaDeclaration(TEST_LOWER_SYMBOL, INT_KEYWORD)),
                renderMagmaFunction("", TEST_LOWER_SYMBOL, renderMagmaDeclaration(TEST_LOWER_SYMBOL, I32_KEYWORD), ""));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void methodParameters(int count) {
        var inputParamString = repeatAndJoin(count, index -> renderJavaDeclaration(TEST_LOWER_SYMBOL + index, INT_KEYWORD), ", ");
        var outputParamString = repeatAndJoin(count, index -> renderMagmaDeclaration(TEST_LOWER_SYMBOL + index, I32_KEYWORD), ", ");

        assertRunWithinClass(
                renderJavaMethod(TEST_LOWER_SYMBOL, inputParamString),
                renderMagmaFunction("", TEST_LOWER_SYMBOL, outputParamString, ""));
    }

    @Test
    void definitionValue() {
        var value = "100";
        assertRunWithinClass(
                renderJavaDefinition(new JavaDefinitionNodeBuilder().withName(TEST_LOWER_SYMBOL).withType(INT_KEYWORD).withValue(value).complete()),
                renderMagmaDefinition(new MagmaDefinitionBuilder().withMutabilityModifier(LET_KEYWORD_WITH_SPACE).withName(TEST_LOWER_SYMBOL).withType(I32_KEYWORD).withValue(value).build()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void className(String name) {
        assertRun(renderJavaClass(name), renderMagmaClass(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void packageStatement(String name) {
        assertRun(PACKAGE_KEYWORD_WITH_SPACE + name + STATEMENT_END + renderJavaClass(TEST_UPPER_SYMBOL), renderMagmaClass(TEST_UPPER_SYMBOL));
    }

    @Test
    void classPublic() {
        assertRun(renderJavaClass(TEST_UPPER_SYMBOL, PUBLIC_KEYWORD_WITH_SPACE), renderMagmaClass(TEST_UPPER_SYMBOL, EXPORT_KEYWORD_WITH_SPACE));
    }
}