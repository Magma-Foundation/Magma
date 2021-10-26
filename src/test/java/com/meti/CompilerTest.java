package com.meti;

import com.meti.feature.Import;
import org.junit.jupiter.api.Test;

import static com.meti.Compiler.renderNativeImport;
import static com.meti.feature.FeatureTest.assertCompile;

class CompilerTest {
    @Test
    void multiple() {
        var input = renderNativeImport("stdio") + ";" + renderNativeImport("stdlib") + ";";
        var first = new CRenderer(new Import("stdio")).render().asString().orElse("");
        var second = new CRenderer(new Import("stdlib")).render().asString().orElse("");
        var output = first + second;
        assertCompile(input, output);
    }
}