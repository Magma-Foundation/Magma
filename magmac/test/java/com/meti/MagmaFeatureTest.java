package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.meti.Application.*;
import static com.meti.CompiledTest.*;
import static org.junit.jupiter.api.Assertions.*;

public class MagmaFeatureTest {
    private static void assertMagma(String input) {
        try {
            assertEquals(input, compileJavaToMagma(compileMagmaToJava(input)));
        } catch (CompileException e) {
            fail(e);
        }
    }

    private static void assertMagmaFunctionStatements(String definition) {
        assertMagma(renderMagmaFunction(TEST_UPPER_SYMBOL, renderBlock(definition)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void definitionName(String name) {
        assertMagmaFunctionStatements(renderMutableMagmaDefinition(name, TEST_DEFINITION_TYPE, TEST_STRING));
    }

    @Test
    void definitionType() {
        assertMagmaFunctionStatements(renderMutableMagmaDefinition(TEST_LOWER_SYMBOL, I16_TYPE, TEST_STRING));
    }

    @Test
    void definitionValue() {
        assertMagmaFunctionStatements(renderMutableMagmaDefinition(TEST_LOWER_SYMBOL, I16_TYPE, TEST_NUMERIC));
    }

    @Test
    void definitionConst() {
        assertMagmaFunctionStatements(renderMagmaDefinition(CONST_KEYWORD, TEST_LOWER_SYMBOL, I16_TYPE, TEST_NUMERIC));
    }

    @Test
    void magmaInvalid() {
        assertThrows(CompileException.class, () -> compileMagmaToJava(TEST_UPPER_SYMBOL));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void magmaSimple(String name) {
        assertMagma(renderMagmaFunction(name));
    }

    @Test
    void magmaPublic() {
        assertMagma(EXPORT_KEYWORD + renderMagmaFunction(TEST_UPPER_SYMBOL));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void multipleDefinitions(int count) {
        var input = renderMutableMagmaDefinition(TEST_LOWER_SYMBOL, I16_TYPE, TEST_STRING);
        var joinedInput = IntStream.range(0, count)
                .mapToObj(index -> input)
                .collect(Collectors.joining());

        assertMagmaFunctionStatements(joinedInput);
    }
}
