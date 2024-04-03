package com.meti;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.meti.FeatureTest.*;
import static com.meti.JavaLang.renderJavaImport;
import static com.meti.MagmaLang.renderMagmaImport;

public class ImportFeatureTest {
    private static String join(int count, Function<String, String> mapper) {
        return IntStream.range(0, count)
                .mapToObj(i -> TEST_CHILD + i)
                .map(mapper)
                .collect(Collectors.joining());
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testImportChildren(String child) {
        assertCompile(renderJavaImport(TEST_PARENT, child), renderMagmaImport(TEST_PARENT, child));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "Second"})
    void testImportParents(String parent) {
        assertCompile(renderJavaImport(parent, TEST_CHILD), renderMagmaImport(parent, TEST_CHILD));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void testMultipleImports(int count) {
        var input = join(count, line -> renderJavaImport(TEST_PARENT, line));
        var output = join(count, line -> renderMagmaImport(TEST_PARENT, line));
        assertCompile(input, output);
    }
}
