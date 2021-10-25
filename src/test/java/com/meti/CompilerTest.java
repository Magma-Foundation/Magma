package com.meti;

import org.junit.jupiter.api.Test;

import static com.meti.CompiledTests.assertCompile;
import static com.meti.Compiler.renderIncludeDirective;
import static com.meti.Compiler.renderNativeImport;

class CompilerTest {
    @Test
    void multiple() {
        var input = renderNativeImport("stdio") + ";" + renderNativeImport("stdlib") + ";";
        var output = renderIncludeDirective("stdio") + renderIncludeDirective("stdlib");
        assertCompile(input, output);
    }
}