package com.meti.app;

import static org.junit.jupiter.api.Assertions.*;

public class CompiledTest {
    public static void assertCompile(String input, String output) {
        try {
            assertEquals(output, compileImpl(input));
        } catch (CompileException e) {
            fail(e);
        }
    }

    public static void assertInvalid(String input) {
        assertThrows(CompileException.class, () -> compileImpl(input));
    }

    private static String compileImpl(String input) throws CompileException {
        return new Compiler(input).compile();
    }
}
