package com.meti;

import org.junit.jupiter.api.Test;

import static com.meti.CompiledTests.assertCompile;
import static com.meti.Compiler.renderNativeImport;

class CompilerTest {
    @Test
    void multiple() {
        var input = renderNativeImport("stdio") + ";" + renderNativeImport("stdlib") + ";";
        var output = Compiler.renderC(new Import("stdio")) + Compiler.renderC(new Import("stdlib"));
        assertCompile(input, output);
    }
}