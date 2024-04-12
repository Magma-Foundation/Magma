package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.meti.CompiledTest.*;
import static com.meti.Lang.*;

public class MagmaDefinitionTest {
    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void definitionName(String name) {
        MagmaTest.assertMagmaFunctionStatements(renderMutableMagmaDefinition(name, TEST_DEFINITION_TYPE, TEST_STRING));
    }

    @Test
    void definitionType() {
        MagmaTest.assertMagmaFunctionStatements(renderMutableMagmaDefinition(TEST_LOWER_SYMBOL, I16_TYPE, TEST_STRING));
    }

    @Test
    void definitionValue() {
        MagmaTest.assertMagmaFunctionStatements(renderMutableMagmaDefinition(TEST_LOWER_SYMBOL, I16_TYPE, TEST_NUMERIC));
    }

    @Test
    void definitionConst() {
        MagmaTest.assertMagmaFunctionStatements(renderMagmaDefinition(CONST_KEYWORD_WITH_SPACE, TEST_LOWER_SYMBOL, I16_TYPE, TEST_NUMERIC));
    }

    @Test
    void definitionPub() {
        MagmaTest.assertMagmaFunctionStatements(renderMagmaDefinition(PUB_KEYWORD_WITH_SPACE, CONST_KEYWORD_WITH_SPACE, TEST_LOWER_SYMBOL, I16_TYPE, TEST_NUMERIC));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void multipleDefinitions(int count) {
        var input = renderMutableMagmaDefinition(TEST_LOWER_SYMBOL, I16_TYPE, TEST_STRING);
        var joinedInput = IntStream.range(0, count)
                .mapToObj(index -> input)
                .collect(Collectors.joining());

        MagmaTest.assertMagmaFunctionStatements(joinedInput);
    }


    @Test
    void definitionInObject() {
        var input = renderObject(TEST_UPPER_SYMBOL, renderMutableMagmaDefinition(TEST_LOWER_SYMBOL, I16_TYPE, TEST_STRING)) + renderMagmaFunction(TEST_UPPER_SYMBOL);
        MagmaTest.assertMagma(input);
    }

}
