package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.meti.FeatureTest.*;
import static com.meti.JavaLang.*;
import static com.meti.MagmaLang.*;
import static com.meti.MapNodeBuilder.NodeBuilder;

public class MethodTest {
    @Test
    void methodWithContent() {
    }


    @Test
    void methodsMultiple() {
        assertRunWithinClass(renderJavaMethod("first") + renderJavaMethod("second"),
                renderMagmaFunction(NodeBuilder.string("name", "first").integer("indent", 1).build()) +
                renderMagmaFunction(NodeBuilder.string("name", "second").integer("indent", 1).build()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void methodName(String name) {
        assertRunWithinClass(renderJavaMethod(name), renderMagmaFunction(NodeBuilder.string("name", name)
                .integer("indent", 1)
                .build()));
    }

    @Test
    void methodAnnotation() {
        assertRunWithinClass(
                renderJavaMethod(NodeBuilder
                        .stringList("annotations", List.of("First", "Second"))
                        .string("name", TEST_LOWER_SYMBOL)
                        .string("param-string", "")
                        .build()),
                renderMagmaFunction(NodeBuilder
                        .stringList("annotations", List.of("First", "Second"))
                        .string("name", TEST_LOWER_SYMBOL)
                        .integer("indent", 1)
                        .build()));
    }

    @Test
    void methodParameter() {
        assertRunWithinClass(
                renderJavaMethod(TEST_LOWER_SYMBOL, renderJavaDeclaration(TEST_LOWER_SYMBOL, INT_KEYWORD)),
                renderMagmaFunction(NodeBuilder
                        .string("name", TEST_LOWER_SYMBOL)
                        .string("param-string", renderMagmaDeclaration(TEST_LOWER_SYMBOL, I32_KEYWORD))
                        .integer("indent", 1)
                        .build()));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void methodParameters(int count) {
        var inputParamString = repeatAndJoin(count, index -> renderJavaDeclaration(TEST_LOWER_SYMBOL + index, INT_KEYWORD), ", ");
        var outputParamString = repeatAndJoin(count, index -> renderMagmaDeclaration(TEST_LOWER_SYMBOL + index, I32_KEYWORD), ", ");

        assertRunWithinClass(
                renderJavaMethod(TEST_LOWER_SYMBOL, inputParamString),
                renderMagmaFunction(NodeBuilder
                        .string("name", TEST_LOWER_SYMBOL)
                        .string("param-string", outputParamString)
                        .integer("indent", 1)
                        .build()));
    }
}
