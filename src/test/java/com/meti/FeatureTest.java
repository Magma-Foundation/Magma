package com.meti;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FeatureTest {
    protected static void assertCompile(String input, String expected) {
        var output = new Compiler(input).compile();
        assertEquals(expected, output);
    }

    protected static void assertCompile(Node input, Node output) {
        FeatureTest.assertCompile(input.render(), output.render());
    }
}
