package com.meti;

import com.meti.feature.Import;
import org.junit.jupiter.api.Test;

import static com.meti.Compiler.renderNativeImport;
import static com.meti.feature.FeatureTest.assertCompile;

class CompilerTest {
    @Test
    void multiple() {
        var input = renderNativeImport("stdio") + ";" + renderNativeImport("stdlib") + ";";
        var output = Compiler.renderC(new Import("stdio")) + Compiler.renderC(new Import("stdlib"));
        assertCompile(input, output);
    }
}