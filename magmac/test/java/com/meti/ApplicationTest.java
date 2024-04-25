package com.meti;

import com.meti.collect.JavaString;
import com.meti.compile.Compiler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.meti.compile.Compiler.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    public static final String TEST_UPPER_SYMBOL = "Test";
    public static final String TEST_LOWER_SYMBOL = "test";

    private static void assertRun(String input, String output) {
        JavaString input1 = new JavaString(input);
        assertEquals(output, run(input1).value());
    }

    static String renderBeforeClass(String input) {
        return input + Compiler.renderJavaClass(TEST_UPPER_SYMBOL);
    }

    private static void assertRunWithinClass(String input, String output) {
        assertRun(Compiler.renderJavaClass("", TEST_UPPER_SYMBOL, input),
                Compiler.renderMagmaFunctionUnsafe("", TEST_UPPER_SYMBOL, output));
    }

    static String renderBeforeFunction(String before) {
        return before + renderMagmaFunction();
    }

    static String renderMagmaFunction() {
        return Compiler.renderMagmaFunctionUnsafe(TEST_UPPER_SYMBOL);
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void definitionName(String name) {
        assertRunWithinClass(renderJavaDefinition(INT_KEYWORD, name, "0"), renderMagmaDefinitionUnsafe(name, I32_KEYWORD, "0"));
    }

    @Test
    void definitionType() {
        assertRunWithinClass(renderJavaDefinition(LONG_KEYWORD, TEST_LOWER_SYMBOL, "0"), renderMagmaDefinitionUnsafe(TEST_LOWER_SYMBOL, I64_KEYWORD, "0"));
    }

    @Test
    void definitionValue() {
        var value = "100";
        assertRunWithinClass(renderJavaDefinition(LONG_KEYWORD, TEST_LOWER_SYMBOL, value),
                renderMagmaDefinitionUnsafe(TEST_LOWER_SYMBOL, I64_KEYWORD, value));
    }

    @Test
    void definitionPublic() {
        assertRunWithinClass(renderJavaDefinition(PUBLIC_KEYWORD_WITH_SPACE, LONG_KEYWORD, TEST_LOWER_SYMBOL, "0"),
                renderMagmaDefinitionUnsafe(PUBLIC_KEYWORD_WITH_SPACE, TEST_LOWER_SYMBOL, I64_KEYWORD, "0"));
    }

    @Test
    void definitionConst() {
        assertRunWithinClass(renderJavaDefinition(FINAL_KEYWORD_WITH_SPACE, LONG_KEYWORD, TEST_LOWER_SYMBOL, "0"),
                renderMagmaDefinitionUnsafe("", CONST_KEYWORD_WITH_SPACE, TEST_LOWER_SYMBOL, I64_KEYWORD, "0"));
    }

    @Test
    void definitionStatic() {
        assertRun(Compiler.renderJavaClass("", TEST_UPPER_SYMBOL, renderJavaDefinition(STATIC_KEYWORD_WITH_SPACE, LONG_KEYWORD, TEST_LOWER_SYMBOL, "0")),
                Compiler.renderMagmaFunctionUnsafe("", TEST_UPPER_SYMBOL, "") + renderObjectUnsafe(TEST_UPPER_SYMBOL, renderMagmaDefinitionUnsafe("", TEST_LOWER_SYMBOL, I64_KEYWORD, "0")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void importChildren(String child) {
        assertRun(renderBeforeClass(renderJavaImport(TEST_LOWER_SYMBOL, child)), renderBeforeFunction(renderMagmaImport(TEST_LOWER_SYMBOL, child)));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void importMultiple(int count) {
        var inputBefore = IntStream.range(0, count)
                .mapToObj(index -> renderJavaImport(TEST_LOWER_SYMBOL, TEST_UPPER_SYMBOL + index))
                .collect(Collectors.joining());

        var outputBefore = IntStream.range(0, count)
                .mapToObj(index -> renderMagmaImport(TEST_LOWER_SYMBOL, TEST_UPPER_SYMBOL + index))
                .collect(Collectors.joining());

        assertRun(renderBeforeClass(inputBefore), renderBeforeFunction(outputBefore));
    }

    @Test
    void importStatic() {
        var input = renderBeforeClass(renderJavaImport(TEST_LOWER_SYMBOL, TEST_UPPER_SYMBOL, Compiler.STATIC_KEYWORD_WITH_SPACE));
        var output = renderBeforeFunction(renderMagmaImport(TEST_LOWER_SYMBOL, TEST_UPPER_SYMBOL));
        assertRun(input, output);
    }

    @Test
    void importParent() {
        var otherParent = "foo";
        assertRun(renderBeforeClass(renderJavaImport(otherParent, TEST_UPPER_SYMBOL)), renderBeforeFunction(renderMagmaImport(otherParent, TEST_UPPER_SYMBOL)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void packageStatement(String name) {
        assertRun(renderBeforeClass("package " + name + STATEMENT_END), renderMagmaFunction());
    }

    @Test
    void classPublic() {
        assertRun(Compiler.renderJavaClass(PUBLIC_KEYWORD_WITH_SPACE, TEST_UPPER_SYMBOL), Compiler.renderMagmaFunctionUnsafe(EXPORT_KEYWORD_WITH_SPACE, TEST_UPPER_SYMBOL));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void className(String name) {
        assertRun(Compiler.renderJavaClass(name), Compiler.renderMagmaFunctionUnsafe(name));
    }
}
