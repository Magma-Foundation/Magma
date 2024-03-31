package com.meti;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompiledTest {
    static void assertCompile(String input, String expected) {
        var actual = Compiler.compile(input);
        Assertions.assertEquals(expected, actual);
    }
}
