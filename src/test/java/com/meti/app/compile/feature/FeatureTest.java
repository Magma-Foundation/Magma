package com.meti.app.compile.feature;

import com.meti.app.compile.CompileException;
import com.meti.app.compile.Compiler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FeatureTest {
    public static void assertCompile(String input, String output) {
        try {
            var compile = new Compiler(input);
            var actual = compile.compile();
            assertEquals(output, actual);
        } catch (CompileException e) {
            fail(e);
        }
    }
}
