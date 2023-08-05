package com.meti.app.feature;

import org.junit.jupiter.api.Test;

import static com.meti.java.JavaString.Empty;
import static com.meti.java.JavaString.fromSlice;

class ImportTest extends CompiledTest {
    @Test
    void importStatic() {
        assertCompile(fromSlice("import static foo.bar"), fromSlice("import { bar } from foo;\n"));
    }

    @Test
    void importMultiple() {
        assertCompile(fromSlice("import foo.bar;import foo.bas"),
                fromSlice("""
                        import { bar } from foo;
                        import { bas } from foo;
                        """));
    }

    @Test
    void import_() {
        assertCompile(fromSlice("import foo.bar"), fromSlice("import { bar } from foo;\n"));
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