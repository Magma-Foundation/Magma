package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    public static final String TEST_UPPER_SYMBOL = "Test";
    public static final String TEST_LOWER_SYMBOL = "test";

    private static void assertRun(String input, String output) {
        assertEquals(output, Compiler.run(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void importChildren(String child) {
        assertRun(Compiler.renderBeforeClass(Compiler.renderJavaImport(TEST_LOWER_SYMBOL, child)), Compiler.renderBeforeFunction(Compiler.renderMagmaImport(TEST_LOWER_SYMBOL, child)));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void importMultiple(int count) {
        var inputBefore = IntStream.range(0, count)
                .mapToObj(index -> Compiler.renderJavaImport(TEST_LOWER_SYMBOL, TEST_UPPER_SYMBOL + index))
                .collect(Collectors.joining());

        var outputBefore = IntStream.range(0, count)
                .mapToObj(index -> Compiler.renderMagmaImport(TEST_LOWER_SYMBOL, TEST_UPPER_SYMBOL + index))
                .collect(Collectors.joining());

        assertRun(Compiler.renderBeforeClass(inputBefore), Compiler.renderBeforeFunction(outputBefore));

    }

    @Test
    void importParent() {
        var otherParent = "foo";
        assertRun(Compiler.renderBeforeClass(Compiler.renderJavaImport(otherParent, TEST_UPPER_SYMBOL)), Compiler.renderBeforeFunction(Compiler.renderMagmaImport(otherParent, TEST_UPPER_SYMBOL)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void packageStatement(String name) {
        assertRun(Compiler.renderBeforeClass("package " + name + Compiler.STATEMENT_END), Compiler.renderMagmaFunction());
    }

    @Test
    void classPublic() {
        assertRun(Compiler.renderJavaClass(Compiler.PUBLIC_KEYWORD_WITH_SPACE, TEST_UPPER_SYMBOL), Compiler.renderMagmaFunction(Compiler.EXPORT_KEYWORD_WITH_SPACE, TEST_UPPER_SYMBOL));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void className(String name) {
        assertRun(Compiler.renderJavaClass(name), Compiler.renderMagmaFunction(name));
    }
}
