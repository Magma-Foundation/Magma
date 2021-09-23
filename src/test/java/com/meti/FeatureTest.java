package com.meti;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FeatureTest {
    protected void assertCompile(String input, String output) {
        try {
            var actual = new Compiler(input).compile();
            assertEquals(output, actual);
        } catch (ApplicationException e) {
            fail(e);
        }
    }
}
