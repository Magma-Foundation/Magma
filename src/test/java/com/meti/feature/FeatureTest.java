package com.meti.feature;

import com.meti.Compiler;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FeatureTest {
    public static void assertCompile(String input, String output) {
        var compile = new Compiler(input);
        var actual = compile.compile();
        assertEquals(output, actual);
    }
}
