package com.meti;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FeatureTest {
    protected void assertCompile(String input, String expected) {
        try {
            var compiler = new Compiler(input);
            var output = compiler.compile();
            assertEquals(expected, output);
        } catch (CompileException e) {
            fail(e);
        }
    }
}
