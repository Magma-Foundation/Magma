package com.meti.app;

import com.meti.java.String_;
import org.junit.jupiter.api.Test;

import static com.meti.core.Results.unwrap;
import static com.meti.java.JavaString.Empty;
import static com.meti.java.JavaString.fromSlice;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class CompilerTest {
    private static void assertCompile(String_ input) {
        try {
            var actual = unwrap(new Compiler(input).compile());
            assertEquals("", actual.unwrap());
        } catch (CompileException e) {
            fail(e);
        }
    }

    @Test
    void package_() {
        assertCompile(fromSlice("package test"));
    }

    @Test
    void empty() {
        assertCompile(Empty);
    }
}