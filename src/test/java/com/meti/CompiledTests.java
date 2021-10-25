package com.meti;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompiledTests {
    protected static void assertCompile(String input, String output) {
        var compile = new Compiler(input);
        var actual = compile.compile();
        assertEquals(output, actual);
    }
}
