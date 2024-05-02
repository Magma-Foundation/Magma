package com.meti;

import org.junit.jupiter.api.Assertions;

import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FeatureTest {
    public static final String TEST_LOWER_SYMBOL = "test";
    public static final String TEST_UPPER_SYMBOL = "Test";

    static void assertRun(String input, String output) {
        Assertions.assertEquals(output, Compiler.run(input));
    }

    static void assertRunBeforeClass(String beforeClass, String beforeFunction) {
        assertRun(beforeClass + JavaLang.renderJavaClass(TEST_UPPER_SYMBOL),
                beforeFunction + MagmaLang.renderMagmaClass(TEST_UPPER_SYMBOL));
    }

    static String repeatAndJoin(int count, IntFunction<String> mapper) {
        return repeatAndJoin(count, mapper, "");
    }

    static String repeatAndJoin(int count, IntFunction<String> mapper, String delimiter) {
        return IntStream.range(0, count)
                .mapToObj(mapper)
                .collect(Collectors.joining(delimiter));
    }
}