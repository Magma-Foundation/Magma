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
    @Test
    void definitionFinal() {
        assertRunWithinClass(
                renderJavaDefinition(new MapNodeBuilder()
                        .withString("modifier-string", FINAL_KEYWORD_WITH_SPACE)
                        .withString("name", TEST_LOWER_SYMBOL)
                        .withString("type", INT_KEYWORD)
                        .withString("value", "0").complete()),
                renderMagmaDefinition(new MapNodeBuilder()
                        .withString("mutability-modifier", CONST_KEYWORD_WITH_SPACE)
                        .withString("name", TEST_LOWER_SYMBOL)
                        .withString("type", I32_KEYWORD)
                        .withString("value", "0").complete()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void definitionName(String name) {
        MapNodeBuilder mapNodeBuilder = new MapNodeBuilder();
        MapNodeBuilder mapNodeBuilder1 = mapNodeBuilder.withString("name", name);
        MapNodeBuilder mapNodeBuilder2 = mapNodeBuilder1.withString("type", INT_KEYWORD);

        MapNodeBuilder mapNodeBuilder10 = new MapNodeBuilder().withString("mutability-modifier", LET_KEYWORD_WITH_SPACE);
        MapNodeBuilder mapNodeBuilder20 = mapNodeBuilder10.withString("name", name);
        MapNodeBuilder mapNodeBuilder30 = mapNodeBuilder20.withString("type", I32_KEYWORD);
        MapNodeBuilder mapNodeBuilder0 = mapNodeBuilder30.withString("value", "0");

        assertRunWithinClass(renderJavaDefinition(mapNodeBuilder2.withString("value", "0").complete()),
                renderMagmaDefinition(mapNodeBuilder0.complete()));
    }

    @Test
    void definitionType() {
        MapNodeBuilder mapNodeBuilder = new MapNodeBuilder();
        MapNodeBuilder mapNodeBuilder1 = mapNodeBuilder.withString("name", TEST_LOWER_SYMBOL);
        MapNodeBuilder mapNodeBuilder2 = mapNodeBuilder1.withString("type", LONG_KEYWORD);

        MapNodeBuilder mapNodeBuilder10 = new MapNodeBuilder().withString("mutability-modifier", LET_KEYWORD_WITH_SPACE);
        MapNodeBuilder mapNodeBuilder20 = mapNodeBuilder10.withString("name", TEST_LOWER_SYMBOL);
        MapNodeBuilder mapNodeBuilder30 = mapNodeBuilder20.withString("type", I64_KEYWORD);
        MapNodeBuilder mapNodeBuilder0 = mapNodeBuilder30.withString("value", "0");
        assertRunWithinClass(renderJavaDefinition(mapNodeBuilder2.withString("value", "0").complete()),
                renderMagmaDefinition(mapNodeBuilder0.complete()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void methodName(String name) {
        assertRunWithinClass(renderJavaMethod(name), renderMagmaFunction(new MapNodeBuilder()
                .withString("name", name)
                .complete()));
    }

    @Test
    void methodAnnotation() {
        assertRunWithinClass(
                renderJavaMethod("@Test\n", TEST_LOWER_SYMBOL, ""),
                renderMagmaFunction(new MapNodeBuilder()
                        .withString("annotation-string", "@Test\n")
                        .withString("name", TEST_LOWER_SYMBOL)
                        .complete()));
    }

    @Test
    void methodParameter() {
        assertRunWithinClass(
                renderJavaMethod(TEST_LOWER_SYMBOL, renderJavaDeclaration(TEST_LOWER_SYMBOL, INT_KEYWORD)),
                renderMagmaFunction(new MapNodeBuilder()
                        .withString("name", TEST_LOWER_SYMBOL)
                        .withString("param-string", renderMagmaDeclaration(TEST_LOWER_SYMBOL, I32_KEYWORD))
                        .complete()));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void methodParameters(int count) {
        var inputParamString = repeatAndJoin(count, index -> renderJavaDeclaration(TEST_LOWER_SYMBOL + index, INT_KEYWORD), ", ");
        var outputParamString = repeatAndJoin(count, index -> renderMagmaDeclaration(TEST_LOWER_SYMBOL + index, I32_KEYWORD), ", ");

        assertRunWithinClass(
                renderJavaMethod(TEST_LOWER_SYMBOL, inputParamString),
                renderMagmaFunction(new MapNodeBuilder()
                        .withString("name", TEST_LOWER_SYMBOL)
                        .withString("param-string", outputParamString)
                        .complete()));
    }

    @Test
    void definitionValue() {
        var value = "100";
        MapNodeBuilder mapNodeBuilder = new MapNodeBuilder();
        MapNodeBuilder mapNodeBuilder1 = mapNodeBuilder.withString("name", TEST_LOWER_SYMBOL);
        MapNodeBuilder mapNodeBuilder2 = mapNodeBuilder1.withString("type", INT_KEYWORD);

        MapNodeBuilder mapNodeBuilder10 = new MapNodeBuilder().withString("mutability-modifier", LET_KEYWORD_WITH_SPACE);
        MapNodeBuilder mapNodeBuilder20 = mapNodeBuilder10.withString("name", TEST_LOWER_SYMBOL);
        MapNodeBuilder mapNodeBuilder30 = mapNodeBuilder20.withString("type", I32_KEYWORD);
        MapNodeBuilder mapNodeBuilder0 = mapNodeBuilder30.withString("value", value);
        assertRunWithinClass(
                renderJavaDefinition(mapNodeBuilder2.withString("value", value).complete()),
                renderMagmaDefinition(mapNodeBuilder0.complete()));
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