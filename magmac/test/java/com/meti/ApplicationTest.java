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
import static com.meti.MapNodeBuilder.NodeBuilder;

public class ApplicationTest {
    @Test
    void definitionFinal() {
        var input = NodeBuilder
                .string("modifier-string", FINAL_KEYWORD_WITH_SPACE)
                .string("name", TEST_LOWER_SYMBOL)
                .string("type", INT_KEYWORD)
                .string("value", "0").build();

        var output = NodeBuilder
                .string("mutability-modifier", CONST_KEYWORD_WITH_SPACE)
                .string("name", TEST_LOWER_SYMBOL)
                .string("type", I32_KEYWORD)
                .string("value", "0").build();

        assertRunWithinClass(renderJavaDefinition(input), renderMagmaDefinition(output));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void definitionName(String name) {
        MapNodeBuilder mapNodeBuilder1 = NodeBuilder.string("name", name);
        MapNodeBuilder mapNodeBuilder2 = mapNodeBuilder1.string("type", INT_KEYWORD);

        MapNodeBuilder mapNodeBuilder10 = NodeBuilder.string("mutability-modifier", LET_KEYWORD_WITH_SPACE);
        MapNodeBuilder mapNodeBuilder20 = mapNodeBuilder10.string("name", name);
        MapNodeBuilder mapNodeBuilder30 = mapNodeBuilder20.string("type", I32_KEYWORD);
        MapNodeBuilder mapNodeBuilder0 = mapNodeBuilder30.string("value", "0");

        assertRunWithinClass(renderJavaDefinition(mapNodeBuilder2.string("value", "0").build()),
                renderMagmaDefinition(mapNodeBuilder0.build()));
    }

    @Test
    void definitionType() {
        MapNodeBuilder mapNodeBuilder = NodeBuilder;
        MapNodeBuilder mapNodeBuilder1 = mapNodeBuilder.string("name", TEST_LOWER_SYMBOL);
        MapNodeBuilder mapNodeBuilder2 = mapNodeBuilder1.string("type", LONG_KEYWORD);

        MapNodeBuilder mapNodeBuilder10 = NodeBuilder.string("mutability-modifier", LET_KEYWORD_WITH_SPACE);
        MapNodeBuilder mapNodeBuilder20 = mapNodeBuilder10.string("name", TEST_LOWER_SYMBOL);
        MapNodeBuilder mapNodeBuilder30 = mapNodeBuilder20.string("type", I64_KEYWORD);
        MapNodeBuilder mapNodeBuilder0 = mapNodeBuilder30.string("value", "0");
        assertRunWithinClass(renderJavaDefinition(mapNodeBuilder2.string("value", "0").build()),
                renderMagmaDefinition(mapNodeBuilder0.build()));
    }

    @Test
    void definitionValue() {
        var value = "100";
        MapNodeBuilder mapNodeBuilder = NodeBuilder;
        MapNodeBuilder mapNodeBuilder1 = mapNodeBuilder.string("name", TEST_LOWER_SYMBOL);
        MapNodeBuilder mapNodeBuilder2 = mapNodeBuilder1.string("type", INT_KEYWORD);

        MapNodeBuilder mapNodeBuilder10 = NodeBuilder.string("mutability-modifier", LET_KEYWORD_WITH_SPACE);
        MapNodeBuilder mapNodeBuilder20 = mapNodeBuilder10.string("name", TEST_LOWER_SYMBOL);
        MapNodeBuilder mapNodeBuilder30 = mapNodeBuilder20.string("type", I32_KEYWORD);
        MapNodeBuilder mapNodeBuilder0 = mapNodeBuilder30.string("value", value);
        assertRunWithinClass(
                renderJavaDefinition(mapNodeBuilder2.string("value", value).build()),
                renderMagmaDefinition(mapNodeBuilder0.build()));
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