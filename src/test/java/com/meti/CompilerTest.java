package com.meti;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompilerTest {
    protected void assertCompile(String input, String output) {
        var compiler = new Compiler(new Input(input));
        var actual = compiler.compile();
        assertEquals(output, actual);
    }
}
