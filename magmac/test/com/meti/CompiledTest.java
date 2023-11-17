package com.meti;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompiledTest {
    protected static void assertCompile(String input, String output) {
        var actual = new Compiler(input).compile();
        assertEquals(output, actual);
    }
}
