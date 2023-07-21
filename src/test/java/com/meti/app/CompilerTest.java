package com.meti.app;

import com.meti.java.String_;
import org.junit.jupiter.api.Test;

import static com.meti.core.Results.unwrap;
import static com.meti.java.JavaString.Empty;
import static com.meti.java.JavaString.fromSlice;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class CompilerTest {
    private static void assertEmptyOutput(String_ input) {
        assertCompile(input, Empty);
    }

    private static void assertCompile(String_ input, String_ output) {
        try {
            var actual = unwrap(new Compiler(input).compile());
            assertEquals(output.unwrap(), actual.unwrap());
        } catch (CompileException e) {
            fail(e);
        }
    }

    @Test
    void class_() {
        assertCompile(fromSlice("class Test {}"), fromSlice("class def Test() => {}"));
    }

    @Test
    void importStatic() {
        assertCompile(fromSlice("import static foo.bar"), fromSlice("import { bar } from foo;\n"));
    }

    @Test
    void importMultiple() {
        assertCompile(
                fromSlice("import foo.bar;import foo.bas"),
                fromSlice(
                        "import { bar } from foo;\n" +
                        "import { bas } from foo;"));
    }

    @Test
    void import_() {
        assertCompile(fromSlice("import foo.bar"), fromSlice("import { bar } from foo;"));
    }

    @Test
    void package_() {
        assertEmptyOutput(fromSlice("package test"));
    }

    @Test
    void empty() {
        assertEmptyOutput(Empty);
    }
}