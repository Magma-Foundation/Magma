package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.meti.Compiler.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    public static final String TEST_LOWER_SYMBOL = "test";

    private static void assertRun(String input, String output) {
        assertEquals(output, run(input));
    }

    private static String repeatAndJoin(int count, IntFunction<String> mapper) {
        return IntStream.range(0, count)
                .mapToObj(mapper)
                .collect(Collectors.joining());
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void importMultiple(int count) {
        var inputImports = repeatAndJoin(count, index -> renderJavaImport(TEST_LOWER_SYMBOL, TEST_UPPER_SYMBOL + index));
        var outputImports = repeatAndJoin(count, index -> renderMagmaImport(TEST_LOWER_SYMBOL, TEST_UPPER_SYMBOL + index));

        assertRun(inputImports + renderJavaClass(TEST_UPPER_SYMBOL),
                outputImports + renderMagmaFunction(TEST_UPPER_SYMBOL));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void importParent(String name) {
        assertRun(renderJavaImport(name, TEST_UPPER_SYMBOL) + renderJavaClass(TEST_UPPER_SYMBOL),
                renderMagmaImport(name, TEST_UPPER_SYMBOL) + renderMagmaFunction(TEST_UPPER_SYMBOL));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void importChild(String name) {
        assertRun(renderJavaImport(TEST_LOWER_SYMBOL, name) + renderJavaClass(TEST_UPPER_SYMBOL),
                renderMagmaImport(TEST_LOWER_SYMBOL, name) + renderMagmaFunction(TEST_UPPER_SYMBOL));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void className(String name) {
        assertRun(renderJavaClass(name), renderMagmaFunction(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void packageStatement(String name) {
        assertRun(PACKAGE_KEYWORD_WITH_SPACE + name + STATEMENT_END + renderJavaClass(TEST_UPPER_SYMBOL), renderMagmaFunction(TEST_UPPER_SYMBOL));
    }

    @Test
    void classPublic() {
        assertRun(renderJavaClass(TEST_UPPER_SYMBOL, PUBLIC_KEYWORD_WITH_SPACE), renderMagmaFunction(TEST_UPPER_SYMBOL, EXPORT_KEYWORD_WITH_SPACE));
    }
}