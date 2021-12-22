package com.meti;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompiledTest {
    public static void assertCompile(String source, String target) {
        var compiler = new MCCompiler(source);
        var output = compiler.compile();
        assertEquals(target, output);
    }
}
