package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.meti.Application.*;
import static com.meti.CompiledTest.*;
import static org.junit.jupiter.api.Assertions.*;

public class JavaFeatureTest {

    private static void assertJava(String input) {
        try {
            assertEquals(input, compileMagmaToJava(compileJavaToMagma(input)));
        } catch (CompileException e) {
            fail(e);
        }
    }

    private static void assertJavaClassContent(String content) {
        assertJava(Lang.renderJavaClass(TEST_UPPER_SYMBOL, Lang.renderBlock(content)));
    }

    @Test
    void javaInvalid() {
        assertThrows(CompileException.class, () -> compileJavaToMagma(""));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void javaSimple(String name) {
        assertJava(Lang.renderJavaClass(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void definitionName(String name) {
        assertJavaClassContent(Lang.renderJavaDefinition(name, TEST_DEFINITION_TYPE, TEST_STRING));
    }

    @Test
    void definitionType() {
        assertJavaClassContent(Lang.renderJavaDefinition(TEST_LOWER_SYMBOL, Lang.INT_TYPE, TEST_STRING));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void multipleDefinitions(int count) {
        var input = Lang.renderJavaDefinition(TEST_LOWER_SYMBOL, Lang.INT_TYPE, TEST_STRING);
        var joinedInput = IntStream.range(0, count)
                .mapToObj(index -> input)
                .collect(Collectors.joining());

        assertJavaClassContent(joinedInput);
    }

    @Test
    void definitionValue() {
        assertJavaClassContent(Lang.renderJavaDefinition(TEST_LOWER_SYMBOL, Lang.INT_TYPE, TEST_NUMERIC));
    }

    @Test
    void finalDefinition() {
        assertJavaClassContent(Lang.renderJavaDefinition(Lang.FINAL_KEYWORD_WITH_SPACE, TEST_LOWER_SYMBOL, Lang.INT_TYPE, TEST_NUMERIC));
    }

    @Test
    void javaPublic() {
        assertJava(Lang.PUBLIC_KEYWORD + Lang.renderJavaClass(TEST_UPPER_SYMBOL));
    }
}
