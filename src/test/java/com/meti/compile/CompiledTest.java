package com.meti.compile;

import static org.junit.jupiter.api.Assertions.*;

public class CompiledTest {
    public static void assertCompile(String source, String target) {
        try {
            var compiler = new MCCompiler(new RootInput(source));
            var output = compiler.compile();
            assertEquals(target, output);
        } catch (CompileException e) {
            fail(e);
        }
    }

    public static void assertDoesNotCompile(String source) {
        assertThrows(CompileException.class, () -> new MCCompiler(new RootInput(source)).compile());
    }
}
