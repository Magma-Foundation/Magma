package com.meti;

import static com.meti.MagmaCompiler.run;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FeatureTest {
    public static final String TEST_PARENT = "org.junit.jupiter.api";
    public static final String TEST_CHILD = "Test";

    static void assertCompile(String input, String output) {
        assertEquals(output, runImpl(input));
    }

    static String runImpl(String s) {
        try {
            return run(s);
        } catch (CompileException e) {
            return fail(e);
        }
    }
}
