package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.meti.FeatureTest.*;
import static com.meti.JavaLang.STATIC_KEYWORD;
import static com.meti.JavaLang.renderJavaImport;
import static com.meti.MagmaLang.renderImport;
import static com.meti.MagmaLang.renderImportWithChildString;

public class ImportFeatureTest {
    private static String join(int count, Function<String, String> mapper) {
        return IntStream.range(0, count)
                .mapToObj(i -> TEST_CHILD + i)
                .map(mapper)
                .collect(Collectors.joining());
    }

    @Test
    void testAll() {
        assertCompile(
                renderJavaImport(STATIC_KEYWORD, TEST_PARENT, "*"),
                renderImportWithChildString(TEST_PARENT, "*")
        );
    }

    @Test
    void testStatic() {
        assertCompile(
                renderJavaImport(JavaLang.STATIC_KEYWORD, TEST_PARENT, TEST_CHILD),
                renderImport(TEST_PARENT, TEST_CHILD)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testImportChildren(String child) {
        assertCompile(renderJavaImport(TEST_PARENT, child), renderImport(TEST_PARENT, child));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "Second"})
    void testImportParents(String parent) {
        assertCompile(renderJavaImport(parent, TEST_CHILD), renderImport(parent, TEST_CHILD));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void testMultipleImports(int count) {
        var input = join(count, line -> renderJavaImport(TEST_PARENT, line));
        var output = join(count, line -> renderImport(TEST_PARENT, line));
        assertCompile(input, output);
    }
}
