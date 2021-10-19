package com.meti;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FeatureTest {
    protected void assertCompile(String input, String expected) {
        var compiler = new Compiler(input);
        var output = compiler.compile();
        assertEquals(expected, output);
    }
}
