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
                        .with("modifier-string", FINAL_KEYWORD_WITH_SPACE)
                        .with("name", TEST_LOWER_SYMBOL)
                        .with("type", INT_KEYWORD)
                        .with("value", "0").complete()),
                renderMagmaDefinition(new MapNodeBuilder()
                        .with("mutability-modifier", CONST_KEYWORD_WITH_SPACE)
                        .with("name", TEST_LOWER_SYMBOL)
                        .with("type", I32_KEYWORD)
                        .with("value", "0").complete()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void definitionName(String name) {
        MapNodeBuilder mapNodeBuilder = new MapNodeBuilder();
        MapNodeBuilder mapNodeBuilder1 = mapNodeBuilder.with("name", name);
        MapNodeBuilder mapNodeBuilder2 = mapNodeBuilder1.with("type", INT_KEYWORD);

        MapNodeBuilder mapNodeBuilder10 = new MapNodeBuilder().with("mutability-modifier", LET_KEYWORD_WITH_SPACE);
        MapNodeBuilder mapNodeBuilder20 = mapNodeBuilder10.with("name", name);
        MapNodeBuilder mapNodeBuilder30 = mapNodeBuilder20.with("type", I32_KEYWORD);
        MapNodeBuilder mapNodeBuilder0 = mapNodeBuilder30.with("value", "0");

        assertRunWithinClass(renderJavaDefinition(mapNodeBuilder2.with("value", "0").complete()),
                renderMagmaDefinition(mapNodeBuilder0.complete()));
    }

    @Test
    void definitionType() {
        MapNodeBuilder mapNodeBuilder = new MapNodeBuilder();
        MapNodeBuilder mapNodeBuilder1 = mapNodeBuilder.with("name", TEST_LOWER_SYMBOL);
        MapNodeBuilder mapNodeBuilder2 = mapNodeBuilder1.with("type", LONG_KEYWORD);

        MapNodeBuilder mapNodeBuilder10 = new MapNodeBuilder().with("mutability-modifier", LET_KEYWORD_WITH_SPACE);
        MapNodeBuilder mapNodeBuilder20 = mapNodeBuilder10.with("name", TEST_LOWER_SYMBOL);
        MapNodeBuilder mapNodeBuilder30 = mapNodeBuilder20.with("type", I64_KEYWORD);
        MapNodeBuilder mapNodeBuilder0 = mapNodeBuilder30.with("value", "0");
        assertRunWithinClass(renderJavaDefinition(mapNodeBuilder2.with("value", "0").complete()),
                renderMagmaDefinition(mapNodeBuilder0.complete()));
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
                renderMagmaFunction(new MapNodeBuilder()
                        .with("modifier-string", "")
                        .with("name", TEST_LOWER_SYMBOL)
                        .with("param-string", renderMagmaDeclaration(TEST_LOWER_SYMBOL, I32_KEYWORD))
                        .with("content", "")
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
                        .with("modifier-string", "")
                        .with("name", TEST_LOWER_SYMBOL)
                        .with("param-string", outputParamString)
                        .with("content", "")
                        .complete()));
    }

    @Test
    void definitionValue() {
        var value = "100";
        MapNodeBuilder mapNodeBuilder = new MapNodeBuilder();
        MapNodeBuilder mapNodeBuilder1 = mapNodeBuilder.with("name", TEST_LOWER_SYMBOL);
        MapNodeBuilder mapNodeBuilder2 = mapNodeBuilder1.with("type", INT_KEYWORD);

        MapNodeBuilder mapNodeBuilder10 = new MapNodeBuilder().with("mutability-modifier", LET_KEYWORD_WITH_SPACE);
        MapNodeBuilder mapNodeBuilder20 = mapNodeBuilder10.with("name", TEST_LOWER_SYMBOL);
        MapNodeBuilder mapNodeBuilder30 = mapNodeBuilder20.with("type", I32_KEYWORD);
        MapNodeBuilder mapNodeBuilder0 = mapNodeBuilder30.with("value", value);
        assertRunWithinClass(
                renderJavaDefinition(mapNodeBuilder2.with("value", value).complete()),
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