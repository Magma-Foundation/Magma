package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.meti.Application.compileJavaToMagma;
import static com.meti.Application.compileMagmaToJava;
import static com.meti.CompiledTest.*;
import static com.meti.Lang.*;
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
        assertJava(renderJavaClass(TEST_UPPER_SYMBOL, renderBlock(content)));
    }

    @Test
    void javaPackage() throws CompileException {
        var namespace = List.of("com", "meti");
        var input = renderPackage(namespace) + renderJavaClass(TEST_UPPER_SYMBOL);
        var magmaSource = compileJavaToMagma(input);
        assertEquals(input, compileMagmaToJava(magmaSource, namespace));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void javaPackage(String name) throws CompileException {
        var input = renderPackage(name) + renderJavaClass(TEST_UPPER_SYMBOL);
        var magmaSource = compileJavaToMagma(input);
        assertEquals(input, compileMagmaToJava(magmaSource, List.of(name)));
    }

    @Test
    void javaInvalid() {
        assertThrows(CompileException.class, () -> compileJavaToMagma(""));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void javaSimple(String name) {
        assertJava(renderJavaClass(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void definitionName(String name) {
        assertJavaClassContent(renderJavaDefinition(name, TEST_DEFINITION_TYPE, TEST_STRING));
    }

    @Test
    void definitionType() {
        assertJavaClassContent(renderJavaDefinition(TEST_LOWER_SYMBOL, INT_TYPE, TEST_STRING));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void multipleDefinitions(int count) {
        var input = renderJavaDefinition(TEST_LOWER_SYMBOL, INT_TYPE, TEST_STRING);
        var joinedInput = IntStream.range(0, count)
                .mapToObj(index -> input)
                .collect(Collectors.joining());

        assertJavaClassContent(joinedInput);
    }

    @Test
    void definitionValue() {
        assertJavaClassContent(renderJavaDefinition(TEST_LOWER_SYMBOL, INT_TYPE, TEST_NUMERIC));
    }

    @Test
    void definitionFinal() {
        assertJavaClassContent(JavaDefinition.render(TEST_LOWER_SYMBOL, INT_TYPE, TEST_NUMERIC, FINAL_KEYWORD_WITH_SPACE));
    }

    @Test
    void definitionPublic() {
        assertJavaClassContent(JavaDefinition.render(TEST_LOWER_SYMBOL, INT_TYPE, TEST_NUMERIC, PUBLIC_KEYWORD_WITH_SPACE));
    }

    @Test
    void definitionStatic() {
        assertJavaClassContent(JavaDefinition.render(TEST_LOWER_SYMBOL, INT_TYPE, TEST_NUMERIC, STATIC_KEYWORD_WITH_SPACE));
    }

    @Test
    void javaPublic() {
        assertJava(PUBLIC_KEYWORD_WITH_SPACE + renderJavaClass(TEST_UPPER_SYMBOL));
    }
}
