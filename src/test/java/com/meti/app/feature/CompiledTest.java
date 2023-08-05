package com.meti.app.feature;

import com.meti.app.compile.CompileException;
import com.meti.app.compile.Compiler;
import com.meti.java.String_;

import static com.meti.core.Results.unwrap;
import static com.meti.java.JavaString.Empty;
import static com.meti.java.JavaString.fromSlice;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CompiledTest {
    protected static void assertEmptyOutput(String_ input) {
        CompiledTest.assertCompile(input, Empty);
    }

    protected static void assertCompile(String input, String output) {
        CompiledTest.assertCompile(fromSlice(input), fromSlice(output));
    }

    protected static void assertCompile(String_ input, String_ output) {
        try {
            var actual = unwrap(new Compiler(input).compile());
            assertEquals(output.unwrap(), actual.unwrap());
        } catch (CompileException e) {
            fail(e);
        }
    }
}
