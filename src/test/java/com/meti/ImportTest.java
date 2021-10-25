package com.meti;

import org.junit.jupiter.api.Test;

import static com.meti.CompiledTests.assertCompile;

class ImportTest {

    @Test
    void another() {
        var input = Compiler.renderNativeImport("stdlib");
        var output = Compiler.renderC(new Import("stdlib"));
        assertCompile(input, output);
    }
}