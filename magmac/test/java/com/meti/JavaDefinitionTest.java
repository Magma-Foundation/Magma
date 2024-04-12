package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.meti.CompiledTest.*;
import static com.meti.CompiledTest.TEST_NUMERIC;
import static com.meti.Lang.*;

public class JavaDefinitionTest {
    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void definitionName(String name) {
        JavaTest.assertJavaClassContent(renderJavaDefinition(name, TEST_DEFINITION_TYPE, TEST_STRING));
    }

    @Test
    void definitionType() {
        JavaTest.assertJavaClassContent(renderJavaDefinition(TEST_LOWER_SYMBOL, INT_TYPE, TEST_STRING));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void multipleDefinitions(int count) {
        var input = renderJavaDefinition(TEST_LOWER_SYMBOL, INT_TYPE, TEST_STRING);
        var joinedInput = IntStream.range(0, count)
                .mapToObj(index -> input)
                .collect(Collectors.joining());

        JavaTest.assertJavaClassContent(joinedInput);
    }

    @Test
    void definitionValue() {
        JavaTest.assertJavaClassContent(renderJavaDefinition(TEST_LOWER_SYMBOL, INT_TYPE, TEST_NUMERIC));
    }

    @Test
    void definitionFinal() {
        JavaTest.assertJavaClassContent(JavaDefinition.render(TEST_LOWER_SYMBOL, INT_TYPE, TEST_NUMERIC, FINAL_KEYWORD_WITH_SPACE));
    }

    @Test
    void definitionPublic() {
        JavaTest.assertJavaClassContent(JavaDefinition.render(TEST_LOWER_SYMBOL, INT_TYPE, TEST_NUMERIC, PUBLIC_KEYWORD_WITH_SPACE));
    }

    @Test
    void definitionStatic() {
        JavaTest.assertJavaClassContent(JavaDefinition.render(TEST_LOWER_SYMBOL, INT_TYPE, TEST_NUMERIC, STATIC_KEYWORD_WITH_SPACE));
    }
}
