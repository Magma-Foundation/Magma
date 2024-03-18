package com.meti.compile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FeatureTest {
    protected static void assertCompile(String input, String output) {
        try {
            var actual = new Compiler(input).compile().$().inner();
            assertEquals(output, actual);
        } catch (CompileException e) {
            fail(e);
        }
    }
}
