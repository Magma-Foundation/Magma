package com.meti;

import org.junit.jupiter.api.Assertions;

public class CompiledTest {
    static void assertCompile(String input, String expected) {
        var actual = Compiler.compile(input);
        Assertions.assertEquals(expected, actual);
    }
}
