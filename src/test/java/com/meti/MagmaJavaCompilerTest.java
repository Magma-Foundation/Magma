package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MagmaJavaCompilerTest {

    @Test
    void compile() {
        var compiler = new MagmaJavaCompiler("import native Test from org.junit.jupiter.api;");
        var output = compiler.compile();
        assertEquals("import org.junit.jupiter.api.Test;class __index__{}", output);
    }
}