package com.meti;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FeatureTest {
    protected void assertCompiles(String input, String output) {
        try {
            var compiler = new Compiler();
            var compiled = compiler.compile("index", input);
            var source = compiled.getSourceContent();
            assertEquals(output, source);
        } catch (ApplicationException e) {
            fail(e);
        }
    }
}
