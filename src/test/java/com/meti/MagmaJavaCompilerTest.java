package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MagmaJavaCompilerTest {
    @Test
    void compile() {
        assertCompile("import native Test from org.junit.jupiter.api;", "import org.junit.jupiter.api.Test;class __index__{}");
    }

    private void assertCompile(String source, String expectedOutput) {
        var compiler = new MagmaJavaCompiler(source, "index");
        var actualOutput = compiler.compile();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void import_braces() {
        assertCompile("import native { foo } from bar;", "import bar.foo;class __index__{}");
    }

    @Test
    void import_empty_braces() {
        assertCompile("import native {} from bar;", "class __index__{}");
    }
}