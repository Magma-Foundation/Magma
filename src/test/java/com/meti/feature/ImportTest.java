package com.meti.feature;

import com.meti.Compiler;
import com.meti.clang.CRenderer;
import org.junit.jupiter.api.Test;

import static com.meti.feature.FeatureTest.assertCompile;

class ImportTest {

    @Test
    void another() {
        var input = Compiler.renderNativeImport("stdlib");
        var output = new CRenderer(new Import("stdlib")).render()
                .asString()
                .orElse("");
        assertCompile(input, output);
    }
}